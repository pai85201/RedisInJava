package cainsgl.redis.core.network;

import cainsgl.redis.core.config.RedisConfig;
import cainsgl.redis.core.config.RedisStaticConfig;
import cainsgl.redis.core.config.Stopable;
import cainsgl.redis.core.utils.EventWorkGroups;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutionException;

public class RedisServer implements Stopable
{
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    ChannelFuture channelFuture;
    EventWorkGroups eventWorkGroups;
    public RedisServer start(RedisConfig r) throws Exception
    {
        initConfig();
        int groupThreads = this.getBoosGroupThreads();

        EventLoopGroup bossGroup = new NioEventLoopGroup(groupThreads, new DefaultThreadFactory("BossGroup", Thread.NORM_PRIORITY));
        EventLoopGroup workerGroup = new NioEventLoopGroup(groupThreads, new DefaultThreadFactory("HandlerGroup", Thread.MAX_PRIORITY));
         eventWorkGroups = new EventWorkGroups(bossGroup,workerGroup);
        ServerBootstrap bootstrap = new ServerBootstrap();
        try (bossGroup; workerGroup)
        {
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new RedisServerInitializer(r))
                     .option(ChannelOption.SO_BACKLOG, 128) // 服务端接收连接的队列大小
                     .childOption(ChannelOption.SO_KEEPALIVE, true);
            channelFuture= bootstrap.bind(r.port).sync();
            log.info("Server started success!   Redis Server started on port {}", r.port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e)
        {
            log.error("服务出现错误", e);
        }
        return this;
    }
    public void initConfig() throws Exception
    {
        //new各种命令出来
        new RedisStaticConfig().autoConfig();
    }
    int getBoosGroupThreads()
    {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        int boosGroupThreads;
        boosGroupThreads=cpuCores/3-1;
        if(boosGroupThreads==0)
        {
            boosGroupThreads=1;
        }
        return boosGroupThreads;
    }


    @Override
    public void stop() throws InterruptedException, ExecutionException
    {
       channelFuture.channel().closeFuture().get();
        eventWorkGroups.stop();
    }
}

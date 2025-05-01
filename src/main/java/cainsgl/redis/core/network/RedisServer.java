package cainsgl.redis.core.network;

import cainsgl.redis.core.config.RedisConfig;
import cainsgl.redis.core.config.RedisStaticConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOException;
import java.io.IOException;

public class RedisServer
{
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);
    private final RedisConfig redisConfig;
    public RedisServer(RedisConfig redisConfig)
    {
        this.redisConfig = redisConfig;
    }


    public void start(int port) throws Exception
    {
        initConfig();
        int groupThreads = this.getBoosGroupThreads();
        EventLoopGroup bossGroup = new NioEventLoopGroup(groupThreads, new DefaultThreadFactory("BossGroup", Thread.NORM_PRIORITY));
        EventLoopGroup workerGroup = new NioEventLoopGroup(groupThreads, new DefaultThreadFactory("HandlerGroup", Thread.MAX_PRIORITY));
        ServerBootstrap bootstrap = new ServerBootstrap();
        try (bossGroup; workerGroup)
        {
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new RedisServerInitializer())
                     .option(ChannelOption.SO_BACKLOG, 128) // 服务端接收连接的队列大小
                     .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = bootstrap.bind(port).sync();
            log.info("Server started success!   Redis Server started on port {}", port);
            f.channel().closeFuture().sync();
        } catch (Exception e)
        {
            log.error("服务出现错误", e);
        }
    }
    public void initConfig() throws Exception
    {
        //new各种命令出来
        redisConfig.autoConfig();
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
}

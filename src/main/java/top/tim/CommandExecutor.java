package top.tim;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandExecutor implements ModInitializer {

    public static final String MOD_ID = "tim_server_command_executor";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Tim_Server_Command_Executor Mod load!");

        // 注册服务器启动完成事件
        ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
    }

    private void onServerStarted(MinecraftServer server) {
        // 使用ScheduledExecutorService来延迟执行命令
        try (ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor()) {
            executor.schedule(() -> executeStartupCommands(server), 10, TimeUnit.SECONDS);
            // 关闭执行器，避免资源泄漏
            executor.shutdown();
        } catch (Exception e) {
            LOGGER.warn("延迟执行失败: {}", e.getMessage());
        }
    }

    private void executeStartupCommands(MinecraftServer server) {
        try {
            // 记分板显示修改
            server.getCommandManager().executeWithPrefix(
                    server.getCommandSource().withLevel(4),
                    "scoreboard objectives setdisplay below_name time_minutes_persist"
            );
            server.getCommandManager().executeWithPrefix(
                    server.getCommandSource().withLevel(4),
                    "scoreboard objectives setdisplay list time_minutes_persist"
            );

            LOGGER.info("服务器启动指令已执行! ");
        } catch (Exception e) {
            LOGGER.warn("执行启动指令失败: {}", e.getMessage());
        }
    }

}

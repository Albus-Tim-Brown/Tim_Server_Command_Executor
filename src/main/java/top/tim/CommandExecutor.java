package top.tim;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        // 服务器完成启动执行指令
        executeStartupCommands(server);
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

            // 广播
            server.getCommandManager().executeWithPrefix(
                    server.getCommandSource(),
                    "/tellraw @a {\"text\":\"服务器已启动！\",\"color\":\"green\"}"
            );

            LOGGER.info("服务器启动指令已执行！");
        } catch (Exception e) {
            LOGGER.warn("执行启动指令失败: {}", e.getMessage());
        }
    }

}

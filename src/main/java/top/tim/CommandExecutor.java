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
        // 在此处执行你的指令
        executeStartupCommands(server);
    }

    private void executeStartupCommands(MinecraftServer server) {
        try {
            // 示例：设置游戏时间为白天
            server.getCommandManager().executeWithPrefix(
                    server.getCommandSource().withLevel(4),
                    "/time set day"
            );

            // 广播
            server.getCommandManager().executeWithPrefix(
                    server.getCommandSource(),
                    "/tellraw @a {\"text\":\"服务器已启动！\",\"color\":\"green\"}"
            );

            LOGGER.info("服务器启动指令已执行！");
        } catch (Exception e) {
            LOGGER.error("执行启动指令失败: " + e.getMessage());
        }
    }

}

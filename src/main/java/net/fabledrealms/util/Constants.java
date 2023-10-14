package net.fabledrealms.util;

import net.fabledrealms.Core;

public class Constants {

    private static Core core = Core.getPlugin(Core.class);
    public final String prefix = core.getStringUtil().colorString(core.getLangFile().getFile().getString("system.module-prefix"));

}

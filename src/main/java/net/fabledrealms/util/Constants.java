package net.fabledrealms.util;

import net.fabledrealms.Core;

public class Constants {

    private final Core core = Core.getPlugin(Core.class);
    public final String prefix = core.getStringUtil().colorString(core.getLangFile().getFile().getString("system.module-prefix"));
    public static int fixedDay = 5;
}

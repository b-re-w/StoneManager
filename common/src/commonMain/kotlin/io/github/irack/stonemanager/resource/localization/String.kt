package io.github.irack.stonemanager.resource.localization

import androidx.compose.runtime.Composable
import io.github.irack.stonemanager.unit.Hexa
import io.github.irack.stonemanager.unit.Quad
import io.github.irack.stonemanager.util.LString


open class AppString(localeString: String, prior: Boolean = true): LString(localeString, prior) {
    companion object {
        val CURRENT: AppString
            @Composable get() = LString.getCurrentLocalizedString(DEFAULT) as AppString

        val OBJECTS: List<AppString> = listOf(EnUSString, KoKRString, EnGBString, KoKPString)

        val DEFAULT: AppString = OBJECTS[0]
    }

    override val PRIOR: AppString
        get() = super.PRIOR as AppString


    open val appName: String
        get() = PRIOR.appName

    open val appViewHeader: String
        get() = PRIOR.appViewHeader

    open val appViewFooter: String
        get() = PRIOR.appViewFooter

    open val mainConnectedDevice: String
        get() = PRIOR.mainConnectedDevice

    open val mainDeviceConnectionStatus: String
        get() = PRIOR.mainDeviceConnectionStatus

    open val mainLampDescriptor: String
        get() = PRIOR.mainLampDescriptor

    open val mainLampStatus: Hexa<String, String, String, String, String, String>
        get() = Hexa(mainLampStatus0, mainLampStatus1, mainLampStatus2, mainLampStatus3, mainLampStatus4, mainLampStatus5)

    open val mainLampStatus0: String
        get() = PRIOR.mainLampStatus0

    open val mainLampStatus1: String
        get() = PRIOR.mainLampStatus1

    open val mainLampStatus2: String
        get() = PRIOR.mainLampStatus2

    open val mainLampStatus3: String
        get() = PRIOR.mainLampStatus3

    open val mainLampStatus4: String
        get() = PRIOR.mainLampStatus4

    open val mainLampStatus5: String
        get() = PRIOR.mainLampStatus5

    open val mainBatteryDescriptor: String
        get() = PRIOR.mainBatteryDescriptor

    open val mainOnReconnectDescriptor: String
        get() = PRIOR.mainOnReconnectDescriptor

    open val mainOnReconnectStatus: Quad<String, String, String, String>
        get() = Quad(mainOnReconnectStatus0, mainOnReconnectStatus1, mainOnReconnectStatus2, mainOnReconnectStatus3)

    open val mainOnReconnectStatus0: String
        get() = PRIOR.mainOnReconnectStatus0

    open val mainOnReconnectStatus1: String
        get() = PRIOR.mainOnReconnectStatus1

    open val mainOnReconnectStatus2: String
        get() = PRIOR.mainOnReconnectStatus2

    open val mainOnReconnectStatus3: String
        get() = PRIOR.mainOnReconnectStatus3

    open val mainConnectionSchedule: String
        get() = PRIOR.mainConnectionSchedule

    open val mainConnectionScheduleRegister: String
        get() = PRIOR.mainConnectionScheduleRegister

    open val mainConnectionScheduleDeregister: String
        get() = PRIOR.mainConnectionScheduleDeregister

    open val mainReconnectSchedule: String
        get() = PRIOR.mainReconnectSchedule

    open val mainDisconnectSchedule: String
        get() = PRIOR.mainDisconnectSchedule
}

val currentLocalizedString: AppString
    @Composable get() = AppString.CURRENT

val localizedString: AppString
    @Composable get() = currentLocalizedString

val LS: AppString
    @Composable get() = localizedString

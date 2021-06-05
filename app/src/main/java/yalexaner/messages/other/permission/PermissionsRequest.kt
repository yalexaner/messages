package yalexaner.messages.other.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.eazypermissions.common.model.PermissionResult
import yalexaner.messages.MainActivity.Companion.LocalPermissionHandler

@Composable
fun PermissionsRequest(
    permissions: Array<out String>,
    requestCode: Int,
    onGranted: @Composable () -> Unit,
    onDenied: @Composable () -> Unit,
    onDeniedPermanently: (@Composable () -> Unit)? = null,
    rational: (@Composable () -> Unit)? = null,
    awaitResult: (@Composable () -> Unit)? = null,
) {
    val permissionHandler = LocalPermissionHandler.current
    val (permissionResult, setPermissionResult) = remember(permissions) {
        mutableStateOf<PermissionResult?>(null)
    }

    LaunchedEffect(Unit) {
        setPermissionResult(permissionHandler.requestPermissions(requestCode, permissions))
    }

    when (permissionResult) {
        is PermissionResult.PermissionGranted -> onGranted()
        is PermissionResult.PermissionDenied -> onDenied()
        is PermissionResult.PermissionDeniedPermanently -> onDeniedPermanently?.invoke()
        is PermissionResult.ShowRational -> rational?.invoke()
        null -> awaitResult?.invoke()
    }
}
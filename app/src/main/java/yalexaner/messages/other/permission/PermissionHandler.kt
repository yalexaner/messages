package yalexaner.messages.other.permission

import androidx.appcompat.app.AppCompatActivity
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.coroutinespermission.PermissionManager

class PermissionHandler(private val context: AppCompatActivity) {
    suspend fun requestPermissions(
        requestCode: Int,
        permissions: Array<out String>
    ): PermissionResult {
        return PermissionManager.requestPermissions(context, requestCode, *permissions)
    }
}
package com.example.serpro

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ELIMINA TOOBAR
        if (Build.VERSION.SDK_INT > 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setContentView(R.layout.activity_login)
        //PARTE 2 PARA ELIMINAR TOOLBAR
        supportActionBar!!.hide()
        //EVALUA QUE ES ESTA ACTIVIDAD YA ESTUVO EN EJECUCION
        access = true
    }

    companion object {
        var access = false
    }

    //CARGA LA ACTIVIDAD QUE CONTIENE EL LOGIN
    fun cargaLogin(view: View?) {
        val miIntent = Intent(this@LoginActivity, logInRegisterActivity::class.java)
        startActivity(miIntent)
        finish()
    }

    //CARGA LA CLASE PRINCIPAL YA LOGUEADA
    fun cargaHome(view: View?) {
        val miIntent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(miIntent)
        finish()
    }
}
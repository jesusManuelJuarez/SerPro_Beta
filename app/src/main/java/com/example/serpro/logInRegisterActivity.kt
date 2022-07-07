package com.example.serpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class logInRegisterActivity : AppCompatActivity() {

    private var TAG: String = "logInRegisterActivity"
    private var email: String = ""
    private var password: String = ""
    private var status: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_register)

        //PARTE 2 PARA ELIMINAR TOOLBAR
        supportActionBar!!.hide()
        val inputCorreo = findViewById<EditText?>(R.id.inputLoginMail)
        val inputContra = findViewById<EditText?>(R.id.inputLoginPassword)
        val buttonLogin = findViewById<Button?>(R.id.buttonLogin)

        //LISTENING OF THE BUTTON TO ABOUT METHOD ONCLICK
        buttonLogin.setOnClickListener {
            email = inputCorreo.text.toString().trim()
            password = inputContra.text.toString().trim()
            if (email.isNotEmpty()) {
                if (password.isNotEmpty()) {
                    //aqui va el metodo de consulta a la API
                    //CREAMOS EL SERVICIO
                    val queue = Volley.newRequestQueue(this)
                    // uni: 192.168.89.6:8080    datos: 192.168.2.119:8080
                    val url = "http://serproapi.ddns.net/api/usuario/login"
                    val jsonObject = JSONObject()

                    jsonObject.put("correo", email)
                    jsonObject.put("contra", password)

                    val jsonObjectRequest = JsonObjectRequest(url, jsonObject,
                        { response ->
                            //Log.i(TAG, "Response is: $response")
                            //SE EJECUTARA CUANDO RESPONDA EL SERVIDOR
                            //res = "Respuesta: ".format(response.getString("Status"))
                            //SALVAMOS EL STATUS CON "Respuesta:${response.getString("Status")}"
                            status = response.getString("Status")
                        },
                        { error ->
                            error.printStackTrace()
                        }
                    )
                    queue.add(jsonObjectRequest)

                    //CARGA LA SIGUIENTE ACTIVIDAD (MAINACTIVITY DASHBOARD)
                    if (status.equals("Accedio correctamente")) {
                        cargaDashboard()
                    } else {
                        Toast.makeText(
                            this@logInRegisterActivity, "ERROR DE CORREO O CONTRASEÑA",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@logInRegisterActivity, "contraseña Vacía",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    this@logInRegisterActivity, "correo Vacía",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //CARGA LA ACTIVIDAD QUE CONTIENE LA ACTIVIDAD PARA REGISTRO
    fun cargaRegister(view: View?) {
        val miIntent = Intent(this@logInRegisterActivity, RegisterActivity::class.java)
        startActivity(miIntent)
        finish()
    }

    //CARGA MENU DASHBOARD
    fun cargaDashboard() {
        val miIntent = Intent(this@logInRegisterActivity, MainActivity::class.java)
        startActivity(miIntent)
        finish()
    }
}
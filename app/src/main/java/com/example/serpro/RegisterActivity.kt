package com.example.serpro

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    lateinit var nombre: EditText
    lateinit var apellido: EditText
    lateinit var correo: EditText
    lateinit var contra: EditText
    lateinit var reContra: EditText
    lateinit var buttonUp: Button
    val url = "http://serproapi.ddns.net/api/usuario"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ELIMINA TOOBAR
        if (Build.VERSION.SDK_INT > 16) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setContentView(R.layout.activity_register)
        //PARTE 2 PARA ELIMINAR TOOLBAR
        supportActionBar!!.hide()

        //INICIALIZAMOS LAS VARAIBLES
        nombre = findViewById(R.id.inputRegisterName)
        apellido = findViewById(R.id.inputRegisterLastName)
        correo = findViewById(R.id.inputRegisterEmail)
        contra = findViewById(R.id.inputRegisterPassword)
        reContra = findViewById(R.id.inputRegisterConfPassword)
        buttonUp = findViewById(R.id.buttonRegisterRegister)

        buttonUp.setOnClickListener {
            addData(
                nombre.text.toString(),
                apellido.text.toString(),
                correo.text.toString(),
                contra.text.toString(),
                reContra.text.toString()
            )
        }
    }

    private fun addData(
        nombre: String,
        apellido: String,
        correo: String,
        contra: String,
        reContra: String
    ) {
        // INSTANCIA QUEUE
        val queue = Volley.newRequestQueue(this@RegisterActivity)

        val request: StringRequest =
            object : StringRequest(Request.Method.POST, url, object : Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    val jsonObject = JSONObject(response)
                    //MANDA MENSAJE DE ACEPTACIÓN AL USUARIO
                    Toast.makeText(
                        this@RegisterActivity,
                        jsonObject.getString("Status"),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    //CARGA LA ACTIVITY DEL DASHBOARD
                    cargaDashboard()
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    // displaying toast message on response failure.
                    Log.e("tag", "error is " + (error ?: return).message)
                    Toast.makeText(
                        this@RegisterActivity,
                        "Fail to update data..",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }) {
                override fun getParams(): Map<String,String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["nombre"] = nombre
                    params["apellido"] = apellido
                    params["correo"] = correo
                    params["contra"] = contra
                    params["contrados"] = reContra

                    return params
                }

            }
        queue.add(request)
    }

    //CARGA MENU DASHBOARD
    fun cargaDashboard() {
        val miIntent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(miIntent)
        finish()
    }
}
package es.travelworld.registro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextInputEditText textInputEditText = findViewById(R.id.tiet_edad);
        final String[] opciones = {"0-5", "6-11", "12-17", "18-99"};

        textInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("¿Cuántos años tienes?");

                // Configura el adaptador del diálogo
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ignora la selección de las tres primeras opciones
                        if (which < 3) {
                            // Puedes mostrar un mensaje de error o realizar otra acción
                            Toast.makeText(MainActivity.this, "Esta aplicación no es para ti", Toast.LENGTH_SHORT).show();
                        } else {
                            String selectedOption = opciones[which];
                            textInputEditText.setText(selectedOption);
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        TextInputEditText textInputEditText2 = findViewById(R.id.tiet_nombre);
        textInputEditText2.setFilters(new InputFilter[] {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // Expresión regular para permitir solo letras (mayúsculas o minúsculas)
                String regex = "^[a-zA-Z]+$";
                if (!source.toString().matches(regex)) {
                    return "";
                }
                return null;
            }
        }});
        imageButton = findViewById(R.id.ib_camara);

        // Asigna un OnClickListener al ImageButton
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(takePictureIntent);
        }
    }


    // Método para manejar el resultado de la captura de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            android.graphics.Bitmap imageBitmap = (android.graphics.Bitmap) extras.get("data");
            // Hacer algo con la imagen capturada, como mostrarla en un ImageView, etc.
        }
    }
}



package br.com.inaciojr9.retrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.inaciojr9.retrofit.model.Aluno;
import br.com.inaciojr9.retrofit.service.AlunoService;
import br.com.inaciojr9.retrofit.service.ServiceGenerator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAlunoActivity extends AppCompatActivity {

    private Button btnSalvar;
    private EditText edtNome;
    private TextView txvNome;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aluno);

        edtNome = (EditText) findViewById(R.id.edtAddNome);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        listenersButtons();
    }

    /**
     * Chamado ao clicar o botão Salvar
     */
    public void listenersButtons() {

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = new ProgressDialog(AddAlunoActivity.this);
                progress.setTitle("enviando...");
                progress.show();

                String nome = edtNome.getText().toString();
                Aluno aluno = new Aluno(null, nome);

                AlunoService service = ServiceGenerator.createService(AlunoService.class);
                Call<Aluno> call = service.salvar(aluno);

                call.enqueue(new Callback<Aluno>() {
                    @Override
                    public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                        String mensagem = null;
                        if (response.isSuccessful()) {

                            //verifica aqui se o corpo da resposta não é nulo
                            Aluno alunoResp = response.body();
                            if (alunoResp != null) {
                                mensagem = "aluno inserido com o id "+ alunoResp.getId();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);


                            } else{
                                mensagem = response.code() + ": aluno nulo";
                            }

                        } else {

                            mensagem = "Falha na requisição: "+response.code()+": "+response.message();
                            // segura os erros de requisição
                            ResponseBody errorBody = response.errorBody();

                        }
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(),mensagem, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Aluno> call, Throwable t) {
                        t.printStackTrace();
                        String mensagem = "Erro na chamada ao servidor: " + t.getMessage();
                        Toast.makeText(getApplicationContext(),mensagem, Toast.LENGTH_SHORT).show();
                    }
                });

                progress.dismiss();

            }
        });
    }

}
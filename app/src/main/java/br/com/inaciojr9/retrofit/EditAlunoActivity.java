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

public class EditAlunoActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Button btnAlterar;
    private Button btnRemover;
    private EditText edtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_aluno);

        final TextView txvId = (TextView) findViewById(R.id.txvEditAlunoId);
        final EditText edtNome = (EditText) findViewById(R.id.edtEditNome);

        Intent intent = getIntent();
        final Long id = intent.getLongExtra("ID", 0);

        final AlunoService service = ServiceGenerator.createService(AlunoService.class);
        final Call<Aluno> call = service.obterPorId(id);
        dialog = new ProgressDialog(EditAlunoActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Aluno aluno = response.body();
                if(aluno != null){
                    txvId.setText(aluno.getId().toString());
                    edtNome.setText(aluno.getNome());
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();

            }
        });

        btnAlterar = (Button) findViewById(R.id.btnAlterarAluno);

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(EditAlunoActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();
                Aluno aluno = new Aluno(Long.parseLong(txvId.getText().toString()), edtNome.getText().toString());
                Call<Void> call = service.atualizar(id, aluno);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        String mensagem = null;
                        if (response.isSuccessful()) {
                            mensagem = "Aluno alterado com sucesso";
                            if (dialog.isShowing())
                                dialog.dismiss();
                            Toast.makeText(getBaseContext(), mensagem , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        } else {

                            mensagem = "Falha na requisição: "+response.code()+": "+response.message();
                            // segura os erros de requisição
                            ResponseBody errorBody = response.errorBody();

                            if (dialog.isShowing())
                                dialog.dismiss();
                            Toast.makeText(getBaseContext(), mensagem, Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnRemover = (Button) findViewById(R.id.btnDeleteAluno);
        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(EditAlunoActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();
                Call<Void> call = service.excluir(id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Aluno removido com sucesso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}

package com.aplicacion.ejercicio24.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aplicacion.ejercicio24.R;
import com.aplicacion.ejercicio24.configuraciones.Signaturess;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.SignaruressViewHolder> {

    private List<Signaturess> signaturessList;

    public Adaptador(List<Signaturess> signaturessList) {
        this.signaturessList = signaturessList;
    }

    @NonNull
    @Override
    public Adaptador.SignaruressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista, parent, false);

        return new Adaptador.SignaruressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.SignaruressViewHolder holder, int position) {
        holder.setData(signaturessList.get(position));
    }

    @Override
    public int getItemCount() {
        return signaturessList.size();
    }

    class SignaruressViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtNombreC;

        public SignaruressViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imageViewCardView);
            txtNombreC = itemView.findViewById(R.id.txtNombreC);
        }

        void setData(Signaturess signature){
            txtNombreC.setText(signature.descripcion);
            imageView.setImageBitmap(decodeImage(signature.imagen));
        }
    }

    private static Bitmap decodeImage(String encodedImage){

        byte[] bytes = android.util.Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}

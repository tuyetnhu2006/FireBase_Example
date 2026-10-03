package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  private TextView txtTitle;
  private TextView txtContent;
  private TextView txtView;
  private ImageView imgCover;
  private ArticleViewAdapter adapter;
  public ArticleViewHolder(@NonNull View itemView, ArticleViewAdapter adapter) {
    super(itemView);
    txtTitle = itemView.findViewById(R.id.txt_title);
    txtContent = itemView.findViewById(R.id.txt_content);
    txtView = itemView.findViewById(R.id.txt_view);
    imgCover = itemView.findViewById(R.id.img_cover);
    this.adapter = adapter;
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }

  public void setTxtTitle(TextView txtTitle) {
    this.txtTitle = txtTitle;
  }

  public TextView getTxtContent() {
    return txtContent;
  }

  public void setTxtContent(TextView txtContent) {
    this.txtContent = txtContent;
  }

  public TextView getTxtView() {
    return txtView;
  }

  public void setTxtView(TextView txtView) {
    this.txtView = txtView;
  }

  public ImageView getImgCover() {
    return imgCover;
  }

  public void setImgCover(ImageView imgCover) {
    this.imgCover = imgCover;
  }
}
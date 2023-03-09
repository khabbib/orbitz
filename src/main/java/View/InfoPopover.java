package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML controller.
 * Allows access to the matching UI elements.
 */
public class InfoPopover {
    @FXML public ImageView planetImage;
    @FXML public Text infoText;
    @FXML public Label title;
    @FXML public Label infoNum;

    private Image[] images;
    private String[] infoList;

    private int imgIdx = 0;
    private int infoIdx = 0;

    public void setImages(Image[] images) { this.images = images; }
    public void setInfoList(String[] infoList) { this.infoList = infoList; }

    @FXML
    public void onAction_btn_infoNext() {
        if(infoList == null)
            return;

        infoIdx = (infoIdx + 1) % infoList.length;
        infoText.setText(infoList[infoIdx]);
        infoNum.setText(infoIdx+1 + " / " + infoList.length);
    }

    @FXML
    public void onAction_btn_infoPrev() {
        if(infoList == null)
            return;

        infoIdx = (infoIdx - 1);
        if (infoIdx < 0) infoIdx = infoList.length - 1;
        infoText.setText(infoList[infoIdx]);
        infoNum.setText(infoIdx+1 + " / " + infoList.length);
    }



    @FXML
    public void onAction_btn_imgNext() {
        if(images == null)
            return;

        imgIdx = (imgIdx + 1) % images.length;
        planetImage.setImage(images[imgIdx]);
    }

    @FXML
    public void onAction_btn_imgPrev() {
        if(images == null)
            return;

        imgIdx = (imgIdx - 1);
        if (imgIdx < 0) imgIdx = images.length - 1;
        planetImage.setImage(images[imgIdx]);
    }
}

package mines;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML
    private Button resetBTN;

    @FXML
    private TextField widthFLD;

    @FXML
    private TextField heightFLD;

    @FXML
    private TextField minesFLD;

    @FXML
    private ImageView GameImg;
    
    

    
    

    public Button GetResetBTN() {
    	return resetBTN;
    }
    
    public int AppHeight() {
    	//set the Height
    	int height;
    	height = Integer.parseInt(heightFLD.getText());
    	if(height>15)return 15;//max height
		if(height<0)return 0;
		else return height;
    		
    }
    
	public int AppWidth() { 
		//set the Width
		int width;
		width = Integer.parseInt(widthFLD.getText());
		if(width>11)return 11;//max width
		if(width<0)return 0;
		else return width;
	}

	public int InputMines() { 
		//set the Mines
		int mines;
		mines = Integer.parseInt(minesFLD.getText());
		if(mines>=0)
		{
			if(mines>AppHeight()*AppWidth())return 0;
			else return mines;
		}
		else return 0;
	}
}
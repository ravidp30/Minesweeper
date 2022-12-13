package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mines.Mines.Point;

	public class MinesFX extends Application {
		private Mines minesBoard;
		private Button buttons[][];
		private int height, width, numOfMines;
		private HBox hbox;
		private GridPane Gpane;
		private Controller controller;
		private Stage stage;
		private Button ResetButton;
		private boolean isEmpty = false;
		private boolean GameOver = false;
		private Stage stage2;
		private Stage stage3;
		
		

		@Override
		public void start(Stage stage) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("MinesGame.fxml"));
				hbox = loader.load();
				controller = loader.getController();
				this.stage = stage;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			ResetButton = controller.GetResetBTN();
			Scene scene = new Scene(hbox);
			stage.setTitle("Minesweeper -CORONA- Game");
			stage.setScene(scene);
			stage2=stage;
			stage2.show();		     
			isEmpty = true;
			ResetButton.setOnAction(new Reset(hbox));
			
		}

		public static void main(String[] args) {
		//launch the game
			launch(args);
		}

		
		public GridPane GetGrid(int height, int width) {
			// creating the grid of buttons for the game 
			GridPane gridp = new GridPane();
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					buttons[i][j] = new Button();
					buttons[i][j].setPrefSize(50, 50);
					buttons[i][j].setMaxSize(50, 50);
					buttons[i][j].setMinSize(50, 50);
					buttons[i][j].setOnMouseClicked(new MouseRoles(i, j));
					gridp.add(buttons[i][j], i, j);
				}
			}
			gridp.setPadding(new Insets(90));
			return gridp;
		}


		
		class Reset implements EventHandler<ActionEvent> {
			//when reset the game 
			private HBox root;
			public Reset(HBox root) {
				this.root = root;
			}
			@Override
			public void handle(ActionEvent event) { 
				if (!isEmpty)
					root.getChildren().remove(Gpane);
				isEmpty = false;
				GameOver = false;
				height = controller.AppHeight();
				width = controller.AppWidth();
				numOfMines = controller.InputMines();
				if (height == 0 || width == 0) {
					return;
				}
				minesBoard = new Mines(height, width, numOfMines); 
				buttons = new Button[height][width]; 
				Gpane = GetGrid(height, width);
				root.getChildren().add(Gpane); 
				stage.setHeight(width * 60 + 300);
				stage.setWidth(height * 65 + 300);
				
				for (int i = 0; i < height; i++) { 
					for (int j = 0; j < width; j++) {
						buttons[i][j].setText(minesBoard.get(i, j));
					}
				}
			}
		}

		class MouseRoles implements EventHandler<MouseEvent> { 
			// creating the mouse roles in the game
			private int i, j;
			public MouseRoles(int i, int j) {
				this.i = i;
				this.j = j;
			}
			@Override
			public void handle(MouseEvent event) { 
				//Mouse roles right click & left click
				MouseButton clickType = event.getButton(); 
				
				//if (GameOver) { return; }
				if (clickType == MouseButton.PRIMARY && minesBoard.get(i, j) != "F" && !minesBoard.returnOpen(i, j))
					//if left click only and there is no flag && not open 
				{ 
					if (!minesBoard.isDone()) //if not finish
					{
						if (!minesBoard.open(i, j))//if is a mine
						{
							minesBoard.setShowAll(true);//open the board
                             for(int k=0;k<height;k++)
                            	 for(int t=0;t<width;t++) 
                            	 {
                            		 if(minesBoard.get(k,t)=="X")//putting mine image at the mines location
                            		 {
             							 Image img = new Image("mines/corona.png");
            						     ImageView view = new ImageView(img);					     
            						     view.setFitHeight(40);
            						     view.setFitWidth(40);          						     
            						     buttons[k][t].setGraphic(view);           						     
            						             						    
                            		 }
                            		 
                            		 
                            	 }
							GameOver = true;
							//add  picture at losing 
							Image img2 = new Image("mines/pos_test.png");
						     ImageView view2 = new ImageView(img2);	
						     view2.setFitWidth(300);
						     view2.setFitHeight(300);
							  VBox losepane = new VBox();
 						     Stage LosePopup = new Stage();
 						     LosePopup.initModality(Modality.APPLICATION_MODAL);
 						     LosePopup.initOwner(stage2);
 						     losepane.getChildren().add(view2);
 						     Scene loseScene=new Scene(losepane,300,300);
 						     LosePopup.setScene(loseScene);
 						     LosePopup.show();         	
						}
						
						
					}
					if(minesBoard.isDone()) //WINNER
					{
						Image img3 = new Image("mines/WINNER.gif");
					     ImageView view3 = new ImageView(img3);	
					     view3.setFitWidth(300);
					     view3.setFitHeight(300);
						  VBox winpane = new VBox();
					     Stage WinPopup = new Stage();
					     WinPopup.initModality(Modality.APPLICATION_MODAL);
					     WinPopup.initOwner(stage3);
					     winpane.getChildren().add(view3);
					     Scene WinScene=new Scene(winpane,300,300);
					     WinPopup.setScene(WinScene);
					     WinPopup.show();  
					}
			}
						
				for (int x = 0; x < controller.AppHeight(); x++) {
					for (int y = 0; y < controller.AppWidth(); y++) {
						buttons[x][y].setText(minesBoard.get(x, y));
						buttons[x][y].setFont(Font.font(20));
						buttons[x][y].setTextFill(Color.WHITE);
						if(minesBoard.get(x, y)!="." && minesBoard.get(x, y)!="F") 
						{
						buttons[x][y].setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
						buttons[x][y].setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
						}
						
					}
				}
				if (clickType == MouseButton.SECONDARY) 
					//right click used
					
				{ 
					int cnt=1;	
					minesBoard.toggleFlag(i, j);
					buttons[i][j].setText(minesBoard.get(i, j));
					for (int x = 0; x < controller.AppHeight(); x++)
					{
						for (int y = 0; y < controller.AppWidth(); y++)
						{
							buttons[x][y].setText(minesBoard.get(x, y));
							buttons[x][y].setFont(Font.font(20));
							buttons[x][y].setTextFill(Color.WHITE);
							if(minesBoard.get(x, y)=="F") 
							{
							cnt++;
    						Image mask = new Image("mines/mask.png");
   						    ImageView view2 = new ImageView(mask);
   						    view2.setFitHeight(40);
   						    view2.setFitWidth(40);
   						    buttons[x][y].setGraphic(view2); 
   						    	if(cnt==numOfMines && minesBoard.isDone())
   						    		//call for winner
   						    	{
   						    	Image img3 = new Image("mines/WINNER.gif");
   							     ImageView view3 = new ImageView(img3);	
   							     view3.setFitWidth(300);
   							     view3.setFitHeight(300);
   								  VBox winpane = new VBox();
   							     Stage WinPopup = new Stage();
   							     WinPopup.initModality(Modality.APPLICATION_MODAL);
   							     WinPopup.initOwner(stage3);
   							     winpane.getChildren().add(view3);
   							     Scene WinScene=new Scene(winpane,300,300);
   							     WinPopup.setScene(WinScene);
   							     WinPopup.show();  
   						    	}
   						   
							}
							else 
							{
								cnt--;
							 buttons[x][y].setGraphic(null); 
							
							}
						
						}
						
					}
				
			}
		}
	}
}

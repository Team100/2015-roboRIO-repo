package org.team100.smartdashboard;


import javafx.beans.value.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import dashfx.controls.DataAnchorPane;
import dashfx.lib.controls.Category;
import dashfx.lib.controls.DashFXProperties;
import dashfx.lib.controls.Designable;
import dashfx.lib.controls.GroupType;
import dashfx.lib.data.DataCoreProvider;
import dashfx.lib.data.SmartValue;

@Category("Tutorial")
@Designable(value = "X-Y Location", description = "A control to show x/y position in a range")
@GroupType("xyLocation")
@DashFXProperties("Sealed: true, Save Children: false")
public class XYLocation extends DataAnchorPane{
	SmartValue xValue;
	SmartValue yValue;
	Ellipse ellipse;

	private ChangeListener<Object> ychange = new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> ov, Object t, Object t1)
		{
			ellipse.setCenterY(yValue.getData().asNumber() + 10); // offset by radius
		}
	},
	xchange = new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> ov, Object t, Object t1)
		{
			ellipse.setCenterX(xValue.getData().asNumber() + 10); // offset by radius
		}
	};
	
	public XYLocation() {
		super ();

		// we are displaying results by moving the ellipse. initialize it here
		ellipse = new Ellipse(10, 10, 10, 10);
		ellipse.setFill(Color.LIGHTBLUE);
		this.getChildren().add(ellipse); // we inherited from DAP so just add it to ourselves
		nameProperty().addListener(new ChangeListener<String>()
				{
					@Override
					public void changed(ObservableValue<? extends String> ov, String t, String t1)
					{
						unwatch();
						try
						{
							xValue = getObservable("x");
							yValue = getObservable("y");
							rewatch();
						}
						catch(NullPointerException n)
						{
							//fail, ignore, as we must not be registered yet
						}
					}
				});	
			
	}
	

	
	@Override
	public void registered(DataCoreProvider provider)
	{
		super.registered(provider);
		unwatch();
      // if we are being registered, then we can finally get the x and y variable
      // otherwise just unwatch as we are being unregistered
		if (provider != null)
		{
			xValue = getObservable("x");
			yValue = getObservable("y");
			rewatch();
		}
	}
	
	private void rewatch()
	{
		xValue.addListener(xchange);
		yValue.addListener(ychange);
	}
	private void unwatch()
	{
		// this function un-binds all the variable
		if (xValue != null)
			xValue.removeListener(xchange);
		if (yValue != null)
			yValue.removeListener(ychange);
	}
	

	

}

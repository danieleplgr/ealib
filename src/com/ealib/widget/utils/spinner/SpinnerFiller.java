package com.ealib.widget.utils.spinner;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public abstract class SpinnerFiller<L extends Activity, S extends Spinnable> {

	protected List<S> valuesObjectList;
	private int resourceDropDownView;

	public SpinnerFiller() {
		resourceDropDownView = android.R.layout.simple_spinner_dropdown_item;
	}

	public SpinnerFiller(int resourceDropDownView) {
		this.resourceDropDownView = resourceDropDownView;
	}

	public void fill(Context context, Spinner spinnerInstance,
			List<S> valuesObjectList, String promptTitle) {

		this.valuesObjectList = valuesObjectList;
		spinnerInstance.setPrompt(promptTitle);

		ArrayAdapter<S> adapter = new ArrayAdapter<S>(context,
				android.R.layout.simple_spinner_item, valuesObjectList);
		adapter.setDropDownViewResource(this.resourceDropDownView);
		spinnerInstance.setAdapter(adapter);
		spinnerInstance
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View currentView, int position, long id) {
						S selectedItem = SpinnerFiller.this.valuesObjectList
								.get(position);
						SpinnerFiller.this.onSpinnerItemSelected(currentView,
								position, selectedItem);
					}

					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
	}

	protected abstract void onSpinnerItemSelected(View currentView,
			int position, S selectedItem);

}

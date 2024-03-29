package com.alma.telekocsi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alma.telekocsi.checking.ProfileChecking;
import com.alma.telekocsi.dao.profil.Profil;
import com.alma.telekocsi.session.Session;
import com.alma.telekocsi.session.SessionFactory;

public class ProfileSettings extends ARunnableActivity {

	private static final int CHECKING = 1;

	
	private OnClickListener onClickListener = null;
	private Button backButton;
	private Button preferencesSettingsButton;
	private EditText name;
	private EditText firstName;
	private EditText jj;
	private EditText mm;
	private EditText aaaa;
	private EditText phone;
	private TextView nameLabel;
	private TextView firstNameLabel;
	private TextView jjLabel;
	private TextView mmLabel;
	private TextView aaaaLabel;
	private TextView phoneLabel;
	private RadioGroup sexe;
	Session session;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        
        session = SessionFactory.getCurrentSession(this);
        
        setContentView(R.layout.profile_settings);
        backButton = (Button)findViewById(R.id.profile_settings_back_button);
        backButton.setOnClickListener(getOnClickListener()); 
        preferencesSettingsButton = (Button)findViewById(R.id.preferences_settings_button);
        preferencesSettingsButton.setOnClickListener(getOnClickListener());
        
        nameLabel = (TextView)findViewById(R.id.labelname);
        firstNameLabel = (TextView)findViewById(R.id.labelfirstname);
        jjLabel = (TextView)findViewById(R.id.labelday);
        mmLabel = (TextView)findViewById(R.id.lanelmonth);
        aaaaLabel = (TextView)findViewById(R.id.labelyear);
        phoneLabel = (TextView)findViewById(R.id.labelphone);
        
        name = (EditText)findViewById(R.id.respname);
        firstName = (EditText)findViewById(R.id.respfirstname);
        jj = (EditText)findViewById(R.id.respday);
        mm = (EditText)findViewById(R.id.respmonth);
        aaaa = (EditText)findViewById(R.id.respyear);
        phone = (EditText)findViewById(R.id.respphone);
    
        sexe = (RadioGroup)findViewById(R.id.respsex);
        
        //Si l'utilisateur avait deja les preferences on met a jour ces valeurs
        initValues();
    }
	
	private void initValues(){

        if(session!=null && session.isConnected()){
        	
        	Profil profile = session.getActiveProfile();
        	if(profile!=null){
        	
        		name.setText(profile.getNom());
        		firstName.setText(profile.getPrenom());
        		phone.setText(profile.getTelephone());
        		String split[] = profile.getDateNaissance().split("\\/");
        		jj.setText(split[0].trim());
        		mm.setText(split[1].trim());
        		aaaa.setText(split[2].trim());
        		//sex
    			String val  = Profile.getStringVal(profile.getSexe()); 
    				
    			for(int i=0;i<sexe.getChildCount();i++){
    				RadioButton rb = (RadioButton)sexe.getChildAt(i);
    				rb.setChecked(rb.getText().toString().equals(val));
    			}
        	}
        }
	}
	


	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	switch (requestCode) {
    	case CHECKING:
    		switch(resultCode) {
    		case RESULT_OK:{
    			Intent intent = new Intent(this, PreferencesSettings.class);
    			intent = intent.putExtras(getIntent());
    	    	intent = intent.putExtra("name", name.getText().toString());
    	    	intent = intent.putExtra("firstName", firstName.getText().toString());
    	    	intent = intent.putExtra("jj", jj.getText().toString());
    	    	intent = intent.putExtra("mm", mm.getText().toString());
    	    	intent = intent.putExtra("aaaa", aaaa.getText().toString());
    	    	intent = intent.putExtra("phone", phone.getText().toString());
    	    	RadioButton rb = (RadioButton)findViewById(sexe.getCheckedRadioButtonId());
    	    	intent = intent.putExtra("sexe",rb==null?R.string.sex:Profile.getBdVal(rb.getText().toString()));
    			startActivity(intent);
    		}break;
    		case ProfileChecking.INVALID_NAME:
    			Toast.makeText(this, R.string.name_syntaxe_error, Toast.LENGTH_SHORT).show();
    			nameLabel.setTextColor(Color.RED);
    			firstNameLabel.setTextColor(Color.BLACK);
    			jjLabel.setTextColor(Color.BLACK);
    			mmLabel.setTextColor(Color.BLACK);
    			aaaaLabel.setTextColor(Color.BLACK);
    			phoneLabel.setTextColor(Color.BLACK);
    			break;
    		case ProfileChecking.INVALID_FIRST_NAME:
    			Toast.makeText(this, R.string.first_name_syntaxe_error, Toast.LENGTH_SHORT).show();
    			nameLabel.setTextColor(Color.BLACK);
    			firstNameLabel.setTextColor(Color.RED);
    			jjLabel.setTextColor(Color.BLACK);
    			mmLabel.setTextColor(Color.BLACK);
    			aaaaLabel.setTextColor(Color.BLACK);
    			phoneLabel.setTextColor(Color.BLACK);
    			break;
    		case ProfileChecking.INVALID_JJ:
    			Toast.makeText(this, R.string.jj_syntaxe_error, Toast.LENGTH_SHORT).show();
    			nameLabel.setTextColor(Color.BLACK);
    			firstNameLabel.setTextColor(Color.BLACK);
    			jjLabel.setTextColor(Color.RED);
    			mmLabel.setTextColor(Color.BLACK);
    			aaaaLabel.setTextColor(Color.BLACK);
    			phoneLabel.setTextColor(Color.BLACK);
    			break;
    		case ProfileChecking.INVALID_MM:
    			Toast.makeText(this, R.string.mm_syntaxe_error, Toast.LENGTH_SHORT).show();
    			nameLabel.setTextColor(Color.BLACK);
    			firstNameLabel.setTextColor(Color.BLACK);
    			jjLabel.setTextColor(Color.BLACK);
    			mmLabel.setTextColor(Color.RED);
    			aaaaLabel.setTextColor(Color.BLACK);
    			phoneLabel.setTextColor(Color.BLACK);
    			break;
    		case ProfileChecking.INVALID_AAAA:
    			Toast.makeText(this, R.string.aaaa_syntaxe_error, Toast.LENGTH_SHORT).show();
    			nameLabel.setTextColor(Color.BLACK);
    			firstNameLabel.setTextColor(Color.BLACK);
    			jjLabel.setTextColor(Color.BLACK);
    			mmLabel.setTextColor(Color.BLACK);
    			aaaaLabel.setTextColor(Color.RED);
    			phoneLabel.setTextColor(Color.BLACK);
    			break;
    		case ProfileChecking.INVALID_PHONE:
    			Toast.makeText(this, R.string.phone_syntaxe_error, Toast.LENGTH_SHORT).show();
    			nameLabel.setTextColor(Color.BLACK);
    			firstNameLabel.setTextColor(Color.BLACK);
    			jjLabel.setTextColor(Color.BLACK);
    			mmLabel.setTextColor(Color.BLACK);
    			aaaaLabel.setTextColor(Color.BLACK);
    			phoneLabel.setTextColor(Color.RED);
    			break;
    		}
    	}
    	stopProgressDialog();
    }
	
	private OnClickListener getOnClickListener(){
		if(onClickListener==null){
			onClickListener = makeOnClickListener();
		}
		return onClickListener;
	}
	
	private OnClickListener makeOnClickListener(){
		return new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(v==backButton){
					goBack();
				}
				else if(v==preferencesSettingsButton){
					startPreferencesSettings();
				}
			}
			
		};
	}
	
	private void goBack(){
		finish();
	}
	
	private void startPreferencesSettings(){
		startProgressDialogInNewThread(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		session = SessionFactory.getCurrentSession(this);
		if(session.isConnected()){
			MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.options_menu, menu);
		    return true;
		}
		return false;
	}

	@Override
	public void run() {
		Intent intent = new Intent(this, ProfileChecking.class);
    	intent = intent.putExtra("name", name.getText().toString());
    	intent = intent.putExtra("firstName", firstName.getText().toString());
    	intent = intent.putExtra("jj", jj.getText().toString());
    	intent = intent.putExtra("mm", mm.getText().toString());
    	intent = intent.putExtra("aaaa", aaaa.getText().toString());
    	intent = intent.putExtra("phone", phone.getText().toString());
    	startActivityForResult(intent, CHECKING);
	}
	
}


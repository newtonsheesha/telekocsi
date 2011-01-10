package com.alma.telekocsi;

import com.alma.telekocsi.dao.profil.Profil;
import com.alma.telekocsi.session.Session;
import com.alma.telekocsi.session.SessionFactory;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OccupantTab extends ListActivity {

	private OnItemClickListener onItemClickListener = null;
	private ListView listView;
	private Session session = null;
	Profil profile = null;
	
	private  String SEARCH;
	private  String ACTIVATED;
	private  String TRANSACTION;
	
	private  String[] OCCUPANT_FUNCTIONS;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		//Internationalisation
		SEARCH = getString(R.string.route_search);
		ACTIVATED = getString(R.string.activated_route_map_text);
		TRANSACTION = getString(R.string.transaction_validation);
		OCCUPANT_FUNCTIONS = new String[] {
				SEARCH, ACTIVATED, TRANSACTION
		};
		
		setListAdapter(new ArrayAdapter<String>(this
												,R.layout.main_menu_tab_list
												,OCCUPANT_FUNCTIONS)
		);

		listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(getOnItemClickListener());

		session = SessionFactory.getCurrentSession(this);
		profile = session.getActiveProfile();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		session.switchToDriverType();
	}


	@Override
	protected void onStart() {
		super.onStart();
		session.switchToDriverType();
	}


	
	private OnItemClickListener getOnItemClickListener(){
		if(onItemClickListener==null){
			onItemClickListener = makeOnItemClickListener();
		}
		return onItemClickListener;
	}
	
	private OnItemClickListener makeOnItemClickListener(){
		
		return new OnItemClickListener() {
			
		    public void onItemClick(AdapterView<?> parent
		    						,View view
		    						,int position
		    						,long id) {
		    	if(((TextView) view).getText().equals(SEARCH)){
		    		startSearch();
		    	}
		    	else if(((TextView) view).getText().equals(ACTIVATED)){
		    		showActiveRoute();
		    	}
		    	else if(((TextView) view).getText().equals(TRANSACTION)){
		    		startTransaction();
		    	}
		    }
		};
	}
	
	private void startSearch(){
		Intent intent = new Intent(this, TrajetRecherche.class);
		startActivity(intent);
	}
	
	private void startTransaction(){
		Intent intent = new Intent(this, Transaction.class);
		startActivity(intent);
	}

	private void showActiveRoute(){
		startActivity(new Intent(this, GoogleMapActivity.class));
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.disconnection_id:
			disconnect();
			return true;
		case R.id.notifications_id:
			showNotifications();
			return true;
		case R.id.main_menu_id:
			//nothing to do
			return true;
		case R.id.preferences_id:
			showPreferences();
			return true;
		case R.id.profile_id:
			showProfileSettings();
			return true;
		case R.id.routes_id:
			showRoutesManaging();
			return true;
	    }
	    return false;
	}
	
	private void showRoutesManaging(){
		Intent intent = new Intent(this, ItinerairesManaging.class);
		startActivity(intent);
	}
	
	private void disconnect(){
		//fermeture de la session
		SessionFactory.getCurrentSession(this).logout();
		
		//fermeture de lactivite
		finish();
		
		//retour a la page didentification
		Intent intent = new Intent(this, Identification.class);
		startActivity(intent);
	}
	
	private void showNotifications(){
	}

	private void showPreferences(){
		Intent intent = new Intent(this, Preferences.class);
		startActivityForResult(intent, Preferences.RESULT);
	}
	
	private void showProfileSettings(){		
		Intent intent = new Intent(this, ProfileSettings.class);
		startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == Preferences.RESULT) {
			saveProfileAndNotify();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void saveProfileAndNotify(){
		//recuperation des preferences
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		//et du profil du user
		Session session = SessionFactory.getCurrentSession(this);
		Profil profile = session.getActiveProfile();
		Log.d(getClass().getSimpleName(), "saveProfilAndNotify : " + profile);
		//mise a jour
		profile.setEmail(preferences.getString("email", profile.getEmail()));
		profile.setPseudo(preferences.getString("email", profile.getPseudo()));
		profile.setMotDePasse(preferences.getString("password", profile.getMotDePasse()));
		session.saveProfile(profile);
		System.out.println("PROFILE="+profile);
		Log.i(getClass().getName(), profile.toString());
		//notification rapide
		Toast.makeText(this, getString(R.string.profile_creation_ongoing), Toast.LENGTH_SHORT).show();
	}
		
}

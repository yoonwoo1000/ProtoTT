package com.example.protott;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*public class MapLocationConvertActivity extends Activity {
	
	// 10진수 좌표위치(도.10진수)
	TextView latitudeTextView1;
	TextView longitudeTextView1;
	
	// 문자열 좌표위치(도.분.초)
	TextView latitudeTextView2;
	TextView longitudeTextView2;
	
	EditText editText1;  //사용자가 입력한 위치값(도.10진수)
	EditText editText2;  //변환된 도.분.초 위치값
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_location_convert);
	    
		latitudeTextView1 = (TextView)findViewById(R.id.latitudeTextView1);
		longitudeTextView1 = (TextView)findViewById(R.id.longitudeTextVew1);
				
		latitudeTextView2 = (TextView)findViewById(R.id.latitudeTextView2);
		longitudeTextView2 = (TextView)findViewById(R.id.longitudeTextView2);

		//             Location객체에 저장된 값     위도   (북위) 경도       (동경)
		// 주소 변환 - 63빌딩  도.10진 주소         (37.519589,   126.940141)
		//                    도.분.초  주소        37°31'10.5"N 126°56'24.6"E
		//

		//도.10진수          Location.FORMAT_DEGREES
		//도.분.10진수       Location.FORMAT_MINUTES
		//도.분.초.10진수    Location.FORMAT_SECONDS

		double latitude = 37.519589;
		double longitude = 126.640141;
		
		// 주소 - 문자열 주소(도.분.초)
		String strLatitude  = 
				Location.convert(latitude, Location.FORMAT_SECONDS);
		String strLongitude =
				Location.convert(longitude, Location.FORMAT_SECONDS);

		//도.10진수
		latitudeTextView1.setText(Double.toString(latitude));//37.31.10.5204
		longitudeTextView1.setText(Double.toString(longitude));//126.38.10.5076

		//도.분.초
		latitudeTextView2.setText(((latitude > 0) ? " 북위 " : " 남위 ") + strLatitude);
		longitudeTextView2.setText(((longitude > 0) ? " 동경 " : " 서경 ") + strLongitude);
		//------------------------------------------------------------------------
		editText1 = (EditText)findViewById(R.id.editText1);  //도.십진수
		editText2 = (EditText)findViewById(R.id.editText2);  //도.분.초
	}

	public void onClick(View view){
		//     도.10진수
		String strDecimalLocation = editText1.getText().toString();
		//     도.분.초
		String strDegreeLocation = editText2.getText().toString();
		
		if( strDecimalLocation.length() <= 0){
			//도.분.초 문자열 위치정보 --> 도.10진수 위치정보
			//입력시 주의사항 ==> "도:분:초.실수"
			Double decimal = Location.convert(strDegreeLocation);
			editText1.setText(Double.toString(decimal));
		}else{
			//도.10진수 위치정보 --> 도.분.초 문자열 위치정보
			String degree = Location.convert(Double.parseDouble(strDecimalLocation), Location.FORMAT_SECONDS);
			editText2.setText(degree);
		}
	}
}
*/
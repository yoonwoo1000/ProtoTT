package com.example.protott;

/*public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap Map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag3);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);  // 지도를 표시할 준비를 내부적으로 수행(네트워크로 지도를 요청해서 다운받아 Fragment에 표시한다.)
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        Map = googleMap;

        LatLng curPoint = new LatLng(35.823906, 127.153623); //35.824534, 127.153761
        Map.addMarker(new MarkerOptions().position(curPoint).title("전주정보문화산업진흥원"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

    }
}
*/
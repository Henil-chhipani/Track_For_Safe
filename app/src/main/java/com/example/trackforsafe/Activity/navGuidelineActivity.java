package com.example.trackforsafe.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.trackforsafe.utilities.Constants;
import com.example.trackforsafe.utilities.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trackforsafe.Adapter.News_feedAdapter;
import com.example.trackforsafe.Modal.News_feedModal;
import com.example.trackforsafe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class navGuidelineActivity extends AppCompatActivity {

    private TextView title, guide_1, guide_2, guide_3, guide_4, guide_5, guide_6;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_guideline);
        title = findViewById(R.id.title);
        guide_1 = findViewById(R.id.guide_1);
        guide_2 = findViewById(R.id.guide_2);
        guide_3 = findViewById(R.id.guide_3);
        guide_4 = findViewById(R.id.guide_4);
        guide_5 = findViewById(R.id.guide_5);
        guide_6 = findViewById(R.id.guide_6);
        preferenceManager = new PreferenceManager(getApplicationContext());

        if (preferenceManager.getLanguage(Constants.LANGUAGE) == "Hindi") {
            title.setText("प्राकृतिक आपदा के लिए दिशानिर्देश");
            guide_1.setText("1. सूचित रहें और तैयार रहें: मौसम के पूर्वानुमान और स्थानीय अधिकारियों द्वारा दी जाने वाली चेतावनियों से अपडेट रहें। भोजन, पानी, टॉर्च, बैटरी, एक प्राथमिक चिकित्सा किट और आवश्यक दवाओं जैसी आवश्यक आपूर्ति सहित एक चक्रवात आपातकालीन किट तैयार रखें।");
            guide_2.setText("2. अपने घर को सुरक्षित करें: तेज हवाओं से बचाने के लिए खिड़कियों और दरवाजों को शटर या बोर्ड से मजबूत करें। अपने आस-पास की किसी भी ढीली वस्तु को साफ करें जो तेज हवाओं में प्रक्षेप्य बन सकती है। बाहरी फ़र्नीचर, पौधों और अन्य वस्तुओं को घर के अंदर सुरक्षित करें या स्थानांतरित करें।");
            guide_3.setText("3. यदि आवश्यक हो तो खाली करें: यदि अधिकारी निकासी के आदेश जारी करते हैं, तो उनका तुरंत पालन करें। अपने क्षेत्र में निर्दिष्ट निकासी मार्गों और आश्रयों से परिचित हों। यदि आप निचले इलाकों में रहते हैं या तटीय क्षेत्र में तूफान बढ़ने की संभावना है, तो उच्च भूमि को खाली करना विशेष रूप से महत्वपूर्ण है।");
            guide_4.setText("4. घर के अंदर रहें और आश्रय खोजें: यदि आप खाली नहीं कर सकते हैं, तो खिड़कियों और दरवाजों से दूर किसी मजबूत इमारत में शरण लें। अपने घर के सबसे निचले स्तर पर एक छोटे, खिड़की रहित आंतरिक कमरे का उपयोग करें। यदि उपलब्ध हो तो बाथरूम, कोठरी या तहखाने में शरण लें। पावर आउटेज के लिए तैयार रहें और सूचित रहने के लिए बैटरी से चलने वाला रेडियो या मोबाइल डिवाइस रखें।");
            guide_5.setText("5. बाढ़-प्रवण क्षेत्रों से दूर रहें: बाढ़ के पानी में चलने या वाहन चलाने से बचें। सिर्फ छह इंच का बहता पानी आपके पैरों को गिरा सकता है, और बाढ़ का पानी जितना दिखता है उससे कहीं अधिक गहरा हो सकता है। नदियों, नालों और तटीय क्षेत्रों से दूर रहें, जहाँ अचानक बाढ़ या तूफ़ान आने का खतरा हो");
            guide_6.setText("6. संचार बनाए रखें: अपने मोबाइल उपकरणों को चार्ज रखें और संचार के वैकल्पिक तरीके जैसे बैटरी चालित रेडियो रखें। अपने ठिकाने और भलाई के बारे में परिवार और दोस्तों को सूचित करें। यदि आपको सहायता की आवश्यकता है, तो जितनी जल्दी हो सके आपातकालीन सेवाओं से संपर्क करें।");
        } else if (preferenceManager.getLanguage(Constants.LANGUAGE) == "Guj") {
            title.setText("કુદરતી આપત્તિ માટે માર્ગદર્શિકા");
            guide_1.setText("1. જાણ રાખો અને તૈયાર રહો: સ્થાનિક અધિકારીઓ દ્વારા પ્રદાન કરેલ હવામાન અને ચેતવણીઓ પર અપડેટ રહો. તો સહી ખોરાક, પાણી, ફ્લેશલાઇટ, બેટરીઓ, પ્રથમ મદદ બૉક્સ અને જરૂરી દવાઓની સાથે ઘરેલું આપત્તિ પેક તૈયાર રાખો.");
            guide_2.setText("2. તમારા ઘરને સુરક્ષિત બનાવો: મજબૂત હવાનીઓ અને દરવાજાઓને બોર્ડ અથવા પટિયાં વડાવો, જ્વારાતરી વિન્ડનો પ્રતિષ્ઠાન કરવા માટે. ઉચ્ચ હવાને જટાયેલ હવામાનમાં વિમૂઢ વસ્તુઓને હટાવો. બાહ્ય ફર્નીચર, વૃક્ષો અને અન્ય વસ્તુઓને સુરક્ષિત કરો અથવા ઘરમાં નાંખો.");
            guide_3.setText("3.જરૂર પાડવા હોય તો ખાલી કરો: જો અધિકારીઓ ખાલી કરવાની આદેશ આપે છે, તો તેને તક નાખો. તમારા પરિસરમાં નિર્ધારિત ખાલી કરવાની માર્ગીને અને શેલ્ટર્સને ઓળખો. જો તમે નીચી સ્થાનિકતાવાળા અથવા કિનારાના પ્રદેશમાં રહેવાનું હોય, તો વિશેષ રીતે ઉચ્ચ જગ્યા પર ખાલી કરવું મહત્વપૂર્ણ છે.");
            guide_4.setText("4. ઘરમાં રહો અને આશ્રય શોધો: જો તમે ખાલી કરી શકતા નથી, તો જંબીને ઘરમાં પ્રદેશમાં જગ્યા શોધો. તમારા ઘરમાંથી વિન્ડો અને દરવાજાથી દૂર અંદર જગ્યાએ જોવાની જરૂર છે. ઉમરાળમાં, પોશાખી ખાસગીજરાત અથવા બેઝમેન્ટમાં આશ્રય લો. તૈયાર રહો અને જાહેરાત માટે બેટરી પાવરની રેડિયો અથવા મોબાઇલ ડિવાઇસ સાથે રહો.");
            guide_5.setText("5. વરસાદના જગ્યાઓમાંથી દૂર રહો: વરસાદના પાણીમાં ચાલતી નાળિકા પર ચાલી જવા અથવા વાહન ચલાવવા અટકાવો. ચાલતી પાણીમાં છ ઇંચ હલકા જગ્યાએ તમને આપત્તિમાં મૂકી શકે છે અને વરસાદના પાણીમાં વધુ ઊંડુ થઈ શકે છે. વરસાદના નદીઓ, ધારાઓ અને કિનારા પ્રદેશોમાંથી દૂર રહો, કારણ તેમાં પટાકીનું વધારો કરી શકે છે અથવા જ્વારાતરી ઉફાનીમાં પાણી બેરામણ થાય શકે છે.");
            guide_6.setText("6. સંપર્ક બનાવો રાખો: તમારા મોબાઇલ ડિવાઇસને ચાર્જ કરો અને બેટરીમાં સાથે રાખો. તમારા સભ્યો અને મિત્રોને તમારા સ્થળ અને સુરક્ષા વિશે માહિતી આપો. જો તમને મદદની જરૂર હોય, તો શીઘ્રઇ આપત્તિકર સેવાઓનો સંપર્ક કરો.");
        }
    }
}
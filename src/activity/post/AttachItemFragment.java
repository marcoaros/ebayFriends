package activity.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.PostNewUtil;

import activity.about.AboutFragment;
import activity.login.LoginActivity;
import activity.newsfeed.NewsFeedFragment;
import activity.notification.NotificationFragment;
import activity.profile.ProfileFragment;
import activity.setting.SettingFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import bean.PurchasedItem;
import android.widget.ListView;

import com.ebay.ebayfriend.R;

public class AttachItemFragment extends Fragment {

	private ArrayList<HashMap<String, Object>> itemList = new ArrayList<HashMap<String, Object>>() ;
	private ListView lv;
	private ItemListHandler itemListHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.attachitem, container, false);
		TextView windowTitleView = (TextView) getActivity().findViewById(
				R.id.window_title);
		windowTitleView.setText("Post");

		itemListHandler = new ItemListHandler();
		Thread getItemListThread = new GetItemListThread();
		getItemListThread.start();

		lv = (ListView) view.findViewById(R.id.purchased_list);

		return view;
	}

	private void switchToPicProcessFragment(PurchasedItem pi) {
		PicProcessFragment fragment = new PicProcessFragment();
		fragment.setPurchasedItem(pi);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
		Log.e("AttachItemFragment","switch to picProcessFragment");
	}

	private void showItemList(){

		SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(),
				itemList, R.layout.puchased_item,
				new String[] { "date", "name" }, new int[] {
						R.id.purchasedItem_date, R.id.purchasedItem_name });
		lv.setAdapter(listItemAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name = ((TextView) view
						.findViewById(R.id.purchasedItem_name)).getText()
						.toString();
				String date = ((TextView) view
						.findViewById(R.id.purchasedItem_date)).getText()
						.toString();
				switchToPicProcessFragment(new PurchasedItem(name, date));

			}

		});
	}
	/**
	 * get the user's purchase records
	 * 
	 * @param UserId
	 *            the user who concerns
	 * @return PurchasedItems
	 */
	private PurchasedItem[] getBoughtRecords(long UserId) {
		return null;
	}

	class SelectItem implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}

	class GetItemListThread extends Thread {
		@Override
		public void run() {
			itemList = PostNewUtil.getItemList();
			itemListHandler.sendMessage(new Message());
			
		}
	}
	class ItemListHandler extends Handler{
		
		public ItemListHandler(){
			
		}
		public ItemListHandler(Looper l){
			
		}
		@Override
		public void handleMessage(Message msg) {
			showItemList();
			Log.e("AttachItemFragment","invalidate the listview");
		}
	}
}

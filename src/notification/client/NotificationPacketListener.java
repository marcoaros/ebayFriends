package notification.client;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

import android.content.Intent;

/**
 * This class notifies the receiver of incoming notification packets
 * asynchronously.
 * 
 */
public class NotificationPacketListener implements PacketListener {

	private final XmppManager xmppManager;

	public NotificationPacketListener(XmppManager xmppManager) {
		this.xmppManager = xmppManager;
	}

	@Override
	public void processPacket(Packet packet) {

		if (packet instanceof NotificationIQ) {
			NotificationIQ notification = (NotificationIQ) packet;

			if (notification.getChildElementXML().contains(
					"androidpn:iq:notification")) {
				String notificationId = notification.getId();
				String notificationApiKey = notification.getApiKey();
				String notificationTitle = notification.getTitle();
				String notificationMessage = notification.getMessage();
				// String notificationTicker = notification.getTicker();
				String notificationUri = notification.getUri();

				Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
				intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
				intent.putExtra(Constants.NOTIFICATION_API_KEY,
						notificationApiKey);
				intent.putExtra(Constants.NOTIFICATION_TITLE, notificationTitle);
				intent.putExtra(Constants.NOTIFICATION_MESSAGE,
						notificationMessage);
				intent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
				// intent.setData(Uri.parse((new StringBuilder(
				// "notif://notification.androidpn.org/")).append(
				// notificationApiKey).append("/").append(
				// System.currentTimeMillis()).toString()));

				xmppManager.getContext().sendBroadcast(intent);
			}
		}

	}

}

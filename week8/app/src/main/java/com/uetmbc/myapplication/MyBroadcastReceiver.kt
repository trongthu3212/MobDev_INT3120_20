import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.uetmbc.myapplication.MessageListener

class MyBroadcastReceiver : BroadcastReceiver {
    var messageListener: MessageListener? = null;

    constructor(listener: MessageListener)
    {
        messageListener = listener;
    }

    override fun onReceive(context: Context?, intent: Intent?)
    {
        val message: String? = intent?.getStringExtra("message");
        if (message != null)
        {
            messageListener?.OnMessageIncoming(message);
        }
    }
}
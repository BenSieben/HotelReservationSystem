import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Example demonstration of how to utilize
 * the HotelView and set up ActionListeners
 * to react to actions on the view
 */
public class HotelViewDemo {

    public static void main(String[] args) {
        // Use invokeLater to schedule a job for event dispatch thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final HotelView hv = new HotelView();
                final LoginPanel lp = hv.getLoginPanel();
                CustomerPanel gp = hv.getCustomerPanel();
                ManagerPanel mp = hv.getManagerPanel();
                lp.addSignInButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hv.changeCard(HotelView.CUSTOMER_PANEL);
                    }
                });
                lp.addSignUpButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hv.changeCard(HotelView.MANAGER_PANEL);
                    }
                });
                gp.addLogoutButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hv.changeCard(HotelView.LOGIN_PANEL);
                        lp.resetAllFields();
                    }
                });
                mp.addLogoutButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hv.changeCard(HotelView.LOGIN_PANEL);
                        lp.resetAllFields();
                    }
                });
            }
        });
    }
}

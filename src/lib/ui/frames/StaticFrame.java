package lib.ui.frames;

import lib.types.LabelConfig;
import lib.types.PAD;
import lib.ui.menus.MainMenu;
import lib.ui.frames.base.Frame;
import lib.ui.panels.ValuePanel;

import javax.swing.*;
import java.awt.*;

import static lib.utils.Logging.log;

public class StaticFrame extends Frame {

    public enum Layout {
        FULL
    }

    protected Layout currentLayout;

    public StaticFrame(LabelConfig labelConfig) {
        super(labelConfig);
    }

    protected void setLayout(Layout layout) {
        if (layout == currentLayout) {
            return;
        }

        clearLayout();

        if (Layout.FULL == layout) {
            setFullLayout();
        }

        pack();

        currentLayout = layout;
    }

    protected void setFullLayout() {
        setSize(new Dimension(1000, 800));

        // Over the top: controls
        JPanel controlsContainer = new JPanel();

        JButton applyButton = new JButton("Apply");

        /*
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shiftTime(1000);
                log("%s - %s", panels.get(0).getCurrentStartTime(), panels.get(0).getCurrentEndTime());

            }
        });
        //*/
        addToContainer(controlsContainer, applyButton);

        // Top: LabelPanel
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));

        //addToContainer(topContainer, LabelPanel.getInstance(600, 300, labelConfig, false),
        //                             MultipleRadarPanel.getInstance(200, 200, false));

        ///*

        // Middle left: SinglePADPanel for P, A, D
        JPanel middleLeftContainer = new JPanel();
        middleLeftContainer.setLayout(new BoxLayout(middleLeftContainer, BoxLayout.Y_AXIS));
        //addToContainer(middleLeftContainer, new SinglePADPanel(PAD.Type.P, 600, 200, false),
        //                                    new SinglePADPanel(PAD.Type.A, 600, 200, false),
        //                                    new SinglePADPanel(PAD.Type.D, 600, 200, false));

        JPanel middleRightContainer = new JPanel();
        middleRightContainer.setLayout(new BoxLayout(middleRightContainer, BoxLayout.Y_AXIS));
        addToContainer(middleRightContainer, new ValuePanel(PAD.Type.P, 200, 200),
                                             new ValuePanel(PAD.Type.A, 200, 200),
                                             new ValuePanel(PAD.Type.D, 200, 200));

        // Assemble middleContainer
        JPanel middleContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));
        addToContainer(middleContainer, middleLeftContainer, middleRightContainer);

        //*/
        // Assemble panelContainer
        addToContainer(panelContainer, controlsContainer, topContainer, middleContainer);
    }

    @Override
    protected void initPanelContainer() {
        super.initPanelContainer();

        setLayout(Layout.FULL);
    }

    @Override
    public void handleMenuAction(MainMenu.Action action) {
        switch (action) {
            case STAY_ON_TOP:
                toggleStayOnTop();
                break;
            case LAYOUT_FULL:
                setLayout(Layout.FULL);
                break;
            default:
                log("Unknown menu action.");
                break;
        }
    }
}
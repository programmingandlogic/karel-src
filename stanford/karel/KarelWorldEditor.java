/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.MediaTools;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.Enumeration;
import java.util.Vector;
import stanford.karel.Karel;
import stanford.karel.KarelWorld;
import stanford.karel.MapTool;
import stanford.karel.SuperKarel;

class KarelWorldEditor
extends Canvas
implements MouseListener {
    private static String[] BEEPER_BAG = new String[]{"47494638396123002F00D52000CBFFCB663300666600999999999933979764983200986500CC9898", "323200CCCC98FFCC99CB9833CC9966333333643131643164CC660098CC98993333996633976464CC", "CCCCFFCBCBCB6533CC6666CCCC66444444646431DDDDDDCBFFFFFFFFCBEEEEEE777777888888AAAA", "AABBBBBB555555666666440000320000910000910000910000910000910000910000910000910000", "91000091000091000091000091000091000091000091000091000091000091000091000091000091", "000091000021F9040100001E002C0000000023002F004506FF408F70482C1A8FC8A4E7A3785428D0", "07E7615228AF9F69E5C0E5620E182834C40929411C28664D6178BF847537CCA08429A147D1F1B483", "0F19737E5D6F6C6213254668057D62057F5E908415010A1F4A1F210E0E0F0E1B09280F2328091B0F", "A79B24574A080F1413141C014E96AB450A288C0C61111107BE847762091C4705097D727273070D6F", "0775116A150F0D441F01504F18056276616B5FE26C770561B61622260E010207D40F090D15B6469A", "9B2256460A229B0E66F5865CE0402AC08904A71E0400C5E142402421BC75A300E221920F0A097561", "648082AA87253854A8D04B1AB077E00C256095A68FC93F6E0865F8468100850A280014F9D0C0891A", "FF68C19E11EA45E1801D07FA9630F2168E99A43FE40854C0A0F0929058DFA42E53F32D523843154C", "402862210D856E66ED74FBA2319C9D2805AE2028F52028979169E23520D1C1A2DF800A4A7C4C42A2", "44525B1D4A6C4A90C001070B23868070C0785389BE8039209C95D0714262871F7E3041CC5582130B", "1398B0FA57C88527136F3EE8E0B035916C8DF0D8BE75EA40802EB23820C0EC57C180BA810241ADF9", "00C500D14DB670E915CC8E9BB08393609432154A0492D5C1BDD3A3448314EF50BA482F0AC69BD107", "082A1A21512183390AD435561713A68283EC4274008539851C500001EFF8C15618ED3DF14076596C", "33C824FA8DB3C613D4D023C40710A4017F4E4D6C01156224604803450652B0868209DEE9E7A2206F", "D9F1C4064364E1CA3723EE32E15D776C034E08AC51900D1436C978873031A9275101E4090182905C", "39C2143336D55107060D04C0DA864E60F08459D22913941C0E2E90041E6671E0CE8B771D80480900", "D93240028F04C0C12415A481406DB675308004769A1050100021FF0B4D414347436F6E2004031039", "000000015772697474656E20627920474946436F6E76657274657220322E342E33206F66204D6F6E", "6461792C204D61792032352C2031393938003B"};
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private static final int INFINITE = 99999999;
    private static final int PLUS1 = -1;
    private static final int MINUS1 = -2;
    private static final int WALL_TOOL = 1;
    private static final int COLOR_TOOL = 2;
    private static final int ROBOT_TOOL = 3;
    private static final int BEEPER_TOOL = 4;
    private static final int BEEPER_BAG_TOOL = 5;
    private static final int BIG_TOOL_SIZE = 20;
    private static final int COLOR_TOOL_SIZE = 12;
    private static final int KAREL_TOOL_SIZE = 28;
    private static final int BEEPER_TOOL_SIZE = 28;
    private static final int TOOL_SEP = 6;
    private static final int TOOL_Y_DELTA = 8;
    private static final int TOOL_MARGIN = 20;
    private static final int TOOL_X = 8;
    private static final int TOOL_Y = 3;
    private static final int LABEL_SEP = 5;
    private static final int ROBOT_DELTA = 300;
    private static final int ROBOT_SIZE = 22;
    private static final int ROBOT_SEP = 15;
    private static final int SELECTED_PIXELS = 3;
    private static final int WALL_LENGTH = 12;
    private static final int BEEPER_BAG_WIDTH = 35;
    private static final int BEEPER_BAG_HEIGHT = 47;
    private static final int BAG_LABEL_DELTA_Y = 28;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 94;
    private static final Color[] COLORS;
    private static final int NCOLORS;
    private KarelWorld world;
    private Vector tools;
    private MapTool selectedTool;
    private MapTool oldTool;
    private MapTool beeperBagTool;
    private Image beeperBagImage;

    static {
        Color[] arrcolor = new Color[14];
        arrcolor[1] = Color.black;
        arrcolor[2] = Color.darkGray;
        arrcolor[3] = Color.gray;
        arrcolor[4] = Color.lightGray;
        arrcolor[5] = Color.white;
        arrcolor[6] = Color.red;
        arrcolor[7] = Color.pink;
        arrcolor[8] = Color.orange;
        arrcolor[9] = Color.yellow;
        arrcolor[10] = Color.green;
        arrcolor[11] = Color.cyan;
        arrcolor[12] = Color.blue;
        arrcolor[13] = Color.magenta;
        COLORS = arrcolor;
        NCOLORS = COLORS.length;
    }

    public KarelWorldEditor(KarelWorld world) {
        this.world = world;
        this.setBackground(Color.white);
        this.initEditorCanvas();
        this.addMouseListener(this);
    }

    public void initEditorCanvas() {
        this.tools = new Vector();
        int x = 8;
        int y = 3;
        this.createWallTool(x, y, "Draw Wall");
        this.createWallTool(x += 26, y, "Erase Wall");
        this.createBeeperTool(x += 32, y, "Single Beeper", 1);
        this.createBeeperTool(x += 26, y, "Add Beeper", -1);
        this.createBeeperTool(x += 26, y, "Subtract Beeper", -2);
        this.createBeeperTool(x += 26, y, "Clear Beepers", 0);
        this.createBeeperTool(x += 26, y, "Infinite Beepers", 99999999);
        if (this.world.getKarelCount() == 1) {
            Karel karel = this.world.getKarel();
            x = 8;
            this.createBeeperBagTool(x + 56 + 18, y += 28);
            this.createKarelTool(x, y, "East", 1);
            this.createKarelTool(x += 28, y, "North", 0);
            x = 8;
            this.createKarelTool(x, y + 28, "West", 3);
            this.createKarelTool(x += 28, y + 28, "South", 2);
            if (karel instanceof SuperKarel) {
                x = 8;
                y += Math.max(56, 47) + 8;
                int i = 0;
                while (i < NCOLORS) {
                    this.createColorTool(x += 18, y, 12, COLORS[i]);
                    if (COLORS[i] == Color.white) {
                        x = -10;
                        y += 20;
                    }
                    ++i;
                }
            }
        }
        this.selectedTool = (MapTool)this.tools.elementAt(0);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 94);
    }

    public MapTool getSelectedTool() {
        return this.selectedTool;
    }

    public KarelWorld getWorld() {
        return this.world;
    }

    public void drawTools(Graphics g) {
        Enumeration e = this.tools.elements();
        while (e.hasMoreElements()) {
            this.drawTool(g, (MapTool)e.nextElement());
        }
        if (this.beeperBagTool != null) {
            this.drawBeeperBag(g);
        }
    }

    public void drawKarelTool(Graphics g, MapTool tool) {
        this.world.drawFancyKarel(g, tool.x + tool.size / 2, tool.y + tool.size / 2, tool.dir, tool.size);
    }

    public void drawBeeperTool(Graphics g, MapTool tool) {
        int border = tool == this.getSelectedTool() ? 3 : 1;
        KarelWorld.drawBeeper(g, tool.x + tool.size / 2, tool.y + tool.size / 2, 28, tool.beeperDelta, border, this);
    }

    public void drawBeeperBag(Graphics g) {
        int x = this.beeperBagTool.x;
        int y = this.beeperBagTool.y;
        if (this.beeperBagImage == null) {
            this.beeperBagImage = MediaTools.createImage(BEEPER_BAG);
        }
        g.drawImage(this.beeperBagImage, this.beeperBagTool.x, this.beeperBagTool.y, this);
        Karel karel = this.world.getKarel();
        int nBeepers = karel == null ? 0 : karel.getBeepersInBag();
        KarelWorld.drawBeeper(g, x += 17, y += 28, 28, nBeepers, 1, this);
    }

    public void defineTool(MapTool tool) {
        this.tools.addElement(tool);
    }

    public MapTool createWallTool(int x, int y, String label) {
        MapTool tool = new MapTool(1, x, y, 20);
        tool.label = label;
        this.defineTool(tool);
        return tool;
    }

    public MapTool createColorTool(int x, int y, int size, Color color) {
        MapTool tool = new MapTool(2, x, y, size);
        tool.color = color;
        this.defineTool(tool);
        return tool;
    }

    public MapTool createKarelTool(int x, int y, String label, int dir) {
        MapTool tool = new MapTool(3, x, y, 28);
        tool.label = label;
        tool.dir = dir;
        this.defineTool(tool);
        return tool;
    }

    public MapTool createBeeperTool(int x, int y, String label, int beeperDelta) {
        MapTool tool = new MapTool(4, x, y, 20);
        tool.label = label;
        tool.beeperDelta = beeperDelta;
        this.defineTool(tool);
        return tool;
    }

    public MapTool createBeeperBagTool(int x, int y) {
        this.beeperBagTool = new MapTool(5, x, y, 0);
        return this.beeperBagTool;
    }

    public void drawTool(Graphics g, MapTool tool) {
        g.setColor(this.getBackground());
        int span = tool.size + 4 + 1;
        g.fillRect(tool.x - 2, tool.y - 2, span, span);
        g.setColor(Color.black);
        switch (tool.toolClass) {
            case 1: {
                this.drawWallTool(g, tool);
                break;
            }
            case 2: {
                this.drawColorTool(g, tool);
                break;
            }
            case 3: {
                this.drawKarelTool(g, tool);
                break;
            }
            case 4: {
                this.drawBeeperTool(g, tool);
            }
        }
    }

    public void drawWallTool(Graphics g, MapTool tool) {
        int border = tool == this.selectedTool ? 3 : 1;
        this.drawSquare(g, tool.x, tool.y, tool.size, border, null);
        int x = tool.x + (tool.size - 12 + 1) / 2;
        int y = tool.y + (tool.size + 1) / 2;
        if (tool.label.equals("Erase Wall")) {
            g.setColor(Color.gray);
            g.drawRect(x, y - 1, 12, 2);
            g.setColor(Color.black);
        } else {
            g.fillRect(x, y - 1, 12, 2);
        }
    }

    public void drawColorTool(Graphics g, MapTool tool) {
        int border = tool == this.selectedTool ? 3 : 1;
        Color color = null;
        if (tool.color == null) {
            color = null;
            int x = tool.x + tool.size / 2;
            int y = tool.y + tool.size / 2;
            g.setColor(Color.white);
            g.fillRect(tool.x, tool.y, tool.size, tool.size);
            g.setColor(Color.black);
            g.drawLine(x - 1, y, x + 1, y);
            g.drawLine(x, y - 1, x, y + 1);
        } else {
            color = tool.color;
        }
        this.drawSquare(g, tool.x, tool.y, tool.size, border, color);
    }

    public boolean inBeeperBag(Point pt) {
        if (this.beeperBagTool == null) {
            return false;
        }
        int x = this.beeperBagTool.x;
        int y = this.beeperBagTool.y;
        if (pt.x > x && pt.x < x + 35 && pt.y > y && pt.y < y + 47) {
            return true;
        }
        return false;
    }

    public void wallAction(Point pt, int dir) {
        MapTool tool = this.getSelectedTool();
        if (tool.toolClass != 1) {
            return;
        }
        if (tool.label.equals("Draw Wall")) {
            this.world.setWall(pt, dir);
            this.world.repaint();
        } else if (tool.label.equals("Erase Wall")) {
            this.world.clearWall(pt, dir);
            this.world.repaint();
        }
    }

    public void cornerAction(Point pt) {
        MapTool tool = this.getSelectedTool();
        if (tool.toolClass == 2) {
            this.world.setCornerColor(pt, tool.color);
            this.world.repaint();
        } else if (tool.toolClass == 4) {
            int nBeepers = this.world.getBeepersOnCorner(pt);
            nBeepers = KarelWorld.setBeepers(nBeepers, tool.beeperDelta);
            this.world.setBeepersOnCorner(pt, nBeepers);
            this.world.repaint();
        }
    }

    public void toolAction(Point pt) {
        if (this.inBeeperBag(pt)) {
            Karel karel;
            MapTool tool = this.getSelectedTool();
            if (tool == null) {
                return;
            }
            if (tool.toolClass == 4 && (karel = this.world.getKarel()) != null) {
                int nBeepers = karel.getBeepersInBag();
                nBeepers = KarelWorld.setBeepers(nBeepers, tool.beeperDelta);
                karel.setBeepersInBag(nBeepers);
                this.drawBeeperBag(this.getGraphics());
                this.repaint();
            }
        } else {
            MapTool tool = this.findTool(pt);
            if (tool == null) {
                return;
            }
            if (tool.toolClass == 3) {
                Karel karel = this.world.getKarel();
                if (karel != null) {
                    karel.setDirection(tool.dir);
                }
                this.world.repaint();
            } else {
                this.oldTool = this.selectedTool;
                this.selectedTool = tool;
                this.drawTool(this.getGraphics(), tool);
                this.drawTool(this.getGraphics(), this.oldTool);
                this.repaint();
            }
        }
    }

    public void paint(Graphics g) {
        this.drawTools(g);
    }

    public void mousePressed(MouseEvent e) {
        this.toolAction(e.getPoint());
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    private void drawSquare(Graphics g, int x, int y, int size, int border, Color color) {
        if (color != null) {
            g.setColor(color);
            g.fillRect(x, y, size, size);
        }
        g.setColor(Color.black);
        int i = 0;
        while (i < border) {
            g.drawRect(x - i, y - i, size + 2 * i, size + 2 * i);
            ++i;
        }
    }

    private MapTool findTool(Point pt) {
        Enumeration e = this.tools.elements();
        while (e.hasMoreElements()) {
            MapTool tool = (MapTool)e.nextElement();
            if (!tool.contains(pt)) continue;
            return tool;
        }
        return null;
    }
}


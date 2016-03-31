/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.ErrorException;
import acm.util.MediaTools;
import acm.util.Platform;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Vector;
import stanford.karel.Corner;
import stanford.karel.Karel;
import stanford.karel.KarelRegion;
import stanford.karel.KarelWorldListener;
import stanford.karel.KarelWorldMonitor;

class KarelWorld
extends Canvas {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int NORTHEAST = 10;
    public static final int NORTHWEST = 11;
    public static final int SOUTHEAST = 12;
    public static final int SOUTHWEST = 13;
    public static final int CENTER = 14;
    public static final int INFINITE = 99999999;
    public static final int PLUS1 = -1;
    public static final int MINUS1 = -2;
    public static final int BLANKB = -3;
    public static final int SIMPLE = 0;
    public static final int FANCY = 1;
    public static final int LEFT_NUMBER_MARGIN = 16;
    public static final int BOTTOM_NUMBER_MARGIN = 15;
    public static final int DOUBLE_WALL_THRESHOLD = 24;
    public static final int CROSS_THRESHOLD = 11;
    public static final int NUMBER_THRESHOLD = 15;
    public static final Font NUMBER_FONT = new Font("Times", 0, 9);
    public static final double WALL_FRACTION = 0.3;
    public static final double WALL_TOLERANCE = 0.15;
    public static final int MAX_WIDTH = 50;
    public static final int MAX_HEIGHT = 50;
    public static final int MAX_DISPLAY_WIDTH = 316;
    public static final int MAX_DISPLAY_HEIGHT = 315;
    public static final boolean TOKEN_TRACE = false;
    public static final Color BEEPER_COLOR = Color.lightGray;
    public static final Color MARKED_COLOR = Color.darkGray;
    public static final int BEEPER_BORDER = 1;
    public static final String BEEPER_FONT_FAMILY = "Times";
    public static final int MIN_FANCY = 20;
    public static final int MIN_BEEPER = 4;
    public static final int MIN_LABEL = 15;
    public static final double BEEPER_FRACTION = 0.7;
    public static final double SIMPLE_FRACTION = 0.7;
    private static final String[] INFINITY = new String[]{"47494638396109000600F70000FFFFFF980098339999989800111111222222000054CBFFCB003298", "0033660033CC0033FE00323266330066660000659800989800CC9900FE99329800659800CC0099FE", "0098659898999999CC9900FE98009800329800659900CC9800FE3399CB3399FF9999339898659832", "0098650099339998659833CB9833FF9999CC0099FE00336699656698CC9898FF9999323200336600", "32003233006632009833339965009866339900663300983200666600986500CC3300FE3200CC6600", "FE65CCCC98CCFF99FFCC99FFFF993300CC3200FE6600CC6500FECC0033CC0066FE0032FE00653399", "33339966669933669865CC00CCCB00FEFE00CBFE00FE6699CC6598FF9898CC9999FFCB9833CC9966", "FF9933FF9865333333326532323265326565660033653232660066653265CC3300CC6600FE3200FE", "65000066CC0099CC0066FE0098FE00CCCC00FECB00CCFE00FEFE33CC0033FE0066CC0066FE00CB33", "98CC6699FF3399FF659866CC9965FF9898CC9899FF99CCCC00CCFE00FECB00FEFE00993333996633", "9933669865659833CB9966CC9933FF9865FF33CBCB33FFCC33CCFF33FFFF99CB3399FF3399CC6698", "FF65CC98CCCCCCCCCC99FFCBCBFFFF99CCFFCBCBFF99FFFFCBFF3333CB3366CB3333FF3366FF6533", "CB6666CC6633FF6565FFCB3333CB6533CB3365CC6666FF3333FF6633FF3366FF656533CB3333FF33", "33CB6633FF6666CB3366FF3366CC6665FF65CB33CBCC66CCCC33FFCC65FFFF33CCFF65CCFF33FFFF", "65FF66CCCC65FFCC65CCFF65FFFF98CCCC99FFCC99CCFF99FFFFCBCB33CCFF33CCCC66CCFF65FFCC", "33FFFF33FFCC65FFFF65444444656532DDDDDDCBFFFFFFFFCBEEEEEE100000980000001000660000", "000098000066777777888888AAAAAABBBBBB5555556666660000100000224400005400000000CC00", "00DC0000EE0000FE00003200004400880000980000AA0000BA0000CC0000DC0000EE0000FE00CC00", "00DC0000EE0000FE0000004400005400006600007600220000320000AA0000BA0000002200003200", "7600008800000000AA0000BA00007600008800000021F90401000090002C0000000009000600C7FF", "FFFF980098339999989800111111222222000054CBFFCB0032980033660033CC0033FE0032326633", "0066660000659800989800CC9900FE99329800659800CC0099FE0098659898999999CC9900FE9800", "9800329800659900CC9800FE3399CB3399FF99993398986598320098650099339998659833CB9833", "FF9999CC0099FE00336699656698CC9898FF99993232003366003200323300663200983333996500", "9866339900663300983200666600986500CC3300FE3200CC6600FE65CCCC98CCFF99FFCC99FFFF99", "3300CC3200FE6600CC6500FECC0033CC0066FE0032FE0065339933339966669933669865CC00CCCB", "00FEFE00CBFE00FE6699CC6598FF9898CC9999FFCB9833CC9966FF9933FF98653333333265323232", "65326565660033653232660066653265CC3300CC6600FE3200FE65000066CC0099CC0066FE0098FE", "00CCCC00FECB00CCFE00FEFE33CC0033FE0066CC0066FE00CB3398CC6699FF3399FF659866CC9965", "FF9898CC9899FF99CCCC00CCFE00FECB00FEFE009933339966339933669865659833CB9966CC9933", "FF9865FF33CBCB33FFCC33CCFF33FFFF99CB3399FF3399CC6698FF65CC98CCCCCCCCCC99FFCBCBFF", "FF99CCFFCBCBFF99FFFFCBFF3333CB3366CB3333FF3366FF6533CB6666CC6633FF6565FFCB3333CB", "6533CB3365CC6666FF3333FF6633FF3366FF656533CB3333FF3333CB6633FF6666CB3366FF3366CC", "6665FF65CB33CBCC66CCCC33FFCC65FFFF33CCFF65CCFF33FFFF65FF66CCCC65FFCC65CCFF65FFFF", "98CCCC99FFCC99CCFF99FFFFCBCB33CCFF33CCCC66CCFF65FFCC33FFFF33FFCC65FFFF6544444465", "6532DDDDDDCBFFFFFFFFCBEEEEEE100000980000001000660000000098000066777777888888AAAA", "AABBBBBB5555556666660000100000224400005400000000CC0000DC0000EE0000FE000032000044", "00880000980000AA0000BA0000CC0000DC0000EE0000FE00CC0000DC0000EE0000FE000000440000", "5400006600007600220000320000AA0000BA00000022000032007600008800000000AA0000BA0000", "7600008800000008190021091C4810D2BF7F06110A54C870E0C1840E174A2C4830200021FF0B4D41", "4347436F6E2004031039000000015772697474656E20627920474946436F6E76657274657220322E", "342E33206F66204D6F6E6461792C204D61792032352C2031393938003B"};
    private static final int KAREL_INSET = 6;
    private static final double BODY_OFFSET_X = -0.2;
    private static final double BODY_OFFSET_Y = -0.33;
    private static final double BODY_WIDTH = 0.6;
    private static final double BODY_HEIGHT = 0.8;
    private static final double UPPER_NOTCH = 0.15;
    private static final double LOWER_NOTCH = 0.1;
    private static final double SCREEN_OFFSET_X = -0.07;
    private static final double SCREEN_OFFSET_Y = -0.05;
    private static final double SCREEN_WIDTH = 0.3;
    private static final double SCREEN_HEIGHT = 0.4;
    private static final double SLOT_WIDTH = 0.15;
    private static final double FOOT_WIDTH = 0.08;
    private static final double FOOT_LENGTH = 0.2;
    private static final double UPPER_ANKLE = 0.08;
    private static final double LOWER_ANKLE = 0.08;
    private static Image infinityImage;
    private StreamTokenizer tokenizer;
    private KarelWorldMonitor monitor;
    private Karel activeKarel;
    private Karel lastKarel;
    private boolean running;
    private boolean repaintFlag;
    private boolean editMode;
    private boolean numberSquaresFlag;
    private int cols;
    private int rows;
    private int sqSize;
    private int forcedSize;
    private int alignment;
    private int width;
    private int height;
    private int leftMargin;
    private int bottomMargin;
    private String lastClick;
    private String pathname;
    private String title;
    private Corner[][] map;
    private int look;
    private int beeperBag;
    private int lastBeeperCount;
    private NumberFormat speedFormat;
    private Vector karels;
    private Object sizeLock = new Object();
    private Image offscreen;

    public KarelWorld() {
        this.setTitle("Karel World");
        this.setBackground(Color.white);
        KarelWorldListener listener = new KarelWorldListener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        this.addComponentListener(listener);
        this.speedFormat = NumberFormat.getInstance();
        this.speedFormat.setMinimumIntegerDigits(1);
        this.speedFormat.setMaximumIntegerDigits(1);
        this.speedFormat.setMinimumFractionDigits(2);
        this.speedFormat.setMaximumFractionDigits(2);
        this.forcedSize = 0;
        this.numberSquaresFlag = true;
        this.look = 1;
        this.alignment = 14;
        this.karels = new Vector();
        this.setRepaintFlag(true);
    }

    public void init(int cols, int rows) {
        Object object = this.sizeLock;
        synchronized (object) {
            this.cols = cols;
            this.rows = rows;
            this.setDisplayParameters(cols, rows);
            this.editMode = false;
            this.map = new Corner[cols + 2][rows + 2];
            int x = 1;
            while (x <= cols + 1) {
                int y = 1;
                while (y <= rows + 1) {
                    this.map[x][y] = new Corner();
                    this.map[x][y].wallSouth = y == 1 || y == rows + 1;
                    this.map[x][y].wallWest = x == 1 || x == cols + 1;
                    this.map[x][y].color = null;
                    ++y;
                }
                ++x;
            }
            this.repaint();
        }
    }

    public void add(Karel karel) {
        if (this.karels.indexOf(karel) == -1) {
            karel.setWorld(this);
            this.karels.addElement(karel);
        }
        this.repaint();
    }

    public void remove(Karel karel) {
        this.karels.removeElement(karel);
        karel.setWorld(null);
        this.repaint();
    }

    public Karel getKarel() {
        return this.getKarel(0);
    }

    public Karel getKarel(int k) {
        if (k < 0 || k >= this.karels.size()) {
            throw new ErrorException("Illegal Karel index");
        }
        return (Karel)this.karels.elementAt(k);
    }

    public int getKarelCount() {
        return this.karels.size();
    }

    public Karel getKarelOnSquare(int x, int y) {
        Enumeration e = this.karels.elements();
        while (e.hasMoreElements()) {
            Karel karel = (Karel)e.nextElement();
            Point pt = karel.getLocation();
            if (pt.x != x || pt.y != y) continue;
            return karel;
        }
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getPathname() {
        return this.pathname;
    }

    public void setRepaintFlag(boolean flag) {
        this.repaintFlag = flag;
    }

    public boolean getRepaintFlag() {
        return this.repaintFlag;
    }

    public boolean getNumberSquaresFlag() {
        return this.numberSquaresFlag;
    }

    public void setNumberSquaresFlag(boolean flag) {
        this.numberSquaresFlag = flag;
    }

    public int getAlignment() {
        return this.alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getLook() {
        return this.look;
    }

    public void setLook(int look) {
        this.look = look;
    }

    public String getPathName() {
        return this.pathname;
    }

    public void setPathName(String pathname) {
        this.pathname = pathname;
    }

    public boolean getEditMode() {
        return this.editMode;
    }

    public void setEditMode(boolean flag) {
        this.editMode = flag;
    }

    public void updateEditMode(boolean flag) {
        if (this.monitor == null) {
            throw new ErrorException("No map editor defined");
        }
        this.setEditMode(flag);
        this.repaint();
    }

    public void forceSquareSize(int size) {
        this.forcedSize = size;
    }

    public void reset() {
    }

    public int getSquareSize() {
        return this.sqSize;
    }

    public int getColumns() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }

    public boolean outOfBounds(Point pt) {
        return this.outOfBounds(pt.x, pt.y);
    }

    public boolean outOfBounds(int x, int y) {
        if (x >= 1 && x <= this.cols && y >= 1 && y <= this.rows) {
            return false;
        }
        return true;
    }

    public int getBeepersOnCorner(Point pt) {
        return this.getBeepersOnCorner(pt.x, pt.y);
    }

    public int getBeepersOnCorner(int x, int y) {
        return this.map[x][y].nBeepers;
    }

    public void setBeepersOnCorner(Point pt, int nBeepers) {
        this.map[pt.x][pt.y].nBeepers = nBeepers;
        this.updateCorner(pt);
    }

    public void setBeepersOnCorner(int x, int y, int nBeepers) {
        this.setBeepersOnCorner(new Point(x, y), nBeepers);
    }

    public static int adjustBeepers(int nBeepers, int delta) {
        if (nBeepers == 99999999) {
            return 99999999;
        }
        return nBeepers + delta;
    }

    public static int setBeepers(int nBeepers, int delta) {
        if (delta == 99999999) {
            return 99999999;
        }
        if (delta == -1) {
            return nBeepers == 99999999 ? 99999999 : nBeepers + 1;
        }
        if (delta == -2) {
            return nBeepers == 99999999 ? 99999999 : Math.max(0, nBeepers - 1);
        }
        return delta;
    }

    public Color getCornerColor(Point pt) {
        return this.getCornerColor(pt.x, pt.y);
    }

    public Color getCornerColor(int x, int y) {
        return this.map[x][y].color;
    }

    public void setCornerColor(Point pt, Color color) {
        this.map[pt.x][pt.y].color = color;
        this.updateCorner(pt);
    }

    public void setCornerColor(int x, int y, Color color) {
        this.setCornerColor(new Point(x, y), color);
    }

    public boolean checkWall(Point pt, int dir) {
        return this.checkWall(pt.x, pt.y, dir);
    }

    public boolean checkWall(int x, int y, int dir) {
        switch (dir) {
            case 2: {
                return this.map[x][y].wallSouth;
            }
            case 3: {
                return this.map[x][y].wallWest;
            }
            case 0: {
                return this.map[x][y + 1].wallSouth;
            }
            case 1: {
                return this.map[x + 1][y].wallWest;
            }
        }
        return false;
    }

    public void setWall(Point pt, int dir) {
        switch (dir) {
            case 2: {
                this.map[pt.x][pt.y].wallSouth = true;
                break;
            }
            case 3: {
                this.map[pt.x][pt.y].wallWest = true;
                break;
            }
            case 0: {
                this.map[pt.x][pt.y + 1].wallSouth = true;
                break;
            }
            case 1: {
                this.map[pt.x + 1][pt.y].wallWest = true;
            }
        }
        this.updateCorner(pt);
    }

    public void setWall(int x, int y, int dir) {
        this.setWall(new Point(x, y), dir);
    }

    public void clearWall(Point pt, int dir) {
        switch (dir) {
            case 2: {
                this.map[pt.x][pt.y].wallSouth = false;
                break;
            }
            case 3: {
                this.map[pt.x][pt.y].wallWest = false;
                break;
            }
            case 0: {
                this.map[pt.x][pt.y + 1].wallSouth = false;
                break;
            }
            case 1: {
                this.map[pt.x + 1][pt.y].wallWest = false;
            }
        }
        this.updateCorner(pt);
        if (this.sqSize >= 24) {
            Point left = KarelWorld.adjacentPoint(pt, KarelWorld.leftFrom(dir));
            Point right = KarelWorld.adjacentPoint(pt, KarelWorld.rightFrom(dir));
            this.updateCorner(left);
            this.updateCorner(right);
            this.updateCorner(KarelWorld.adjacentPoint(left, dir));
            this.updateCorner(KarelWorld.adjacentPoint(pt, dir));
            this.updateCorner(KarelWorld.adjacentPoint(right, dir));
        }
    }

    public void clearWall(int x, int y, int dir) {
        this.clearWall(new Point(x, y), dir);
    }

    public void updateCorner(int x, int y) {
        this.updateCorner(new Point(x, y));
    }

    public void updateCorner(Point pt) {
        Rectangle r = this.getCornerRect(pt);
        if (this.repaintFlag) {
            this.repaint(r.x, r.y, r.width, r.height);
        }
    }

    public static String directionName(int dir) {
        switch (dir) {
            case 2: {
                return "SOUTH";
            }
            case 3: {
                return "WEST";
            }
            case 0: {
                return "NORTH";
            }
            case 1: {
                return "EAST";
            }
        }
        return null;
    }

    public static int leftFrom(int dir) {
        switch (dir) {
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 0: {
                return 3;
            }
            case 1: {
                return 0;
            }
        }
        return -1;
    }

    public static int rightFrom(int dir) {
        switch (dir) {
            case 2: {
                return 3;
            }
            case 3: {
                return 0;
            }
            case 0: {
                return 1;
            }
            case 1: {
                return 2;
            }
        }
        return -1;
    }

    public static int oppositeDirection(int dir) {
        switch (dir) {
            case 2: {
                return 0;
            }
            case 3: {
                return 1;
            }
            case 0: {
                return 2;
            }
            case 1: {
                return 3;
            }
        }
        return -1;
    }

    public static Point adjacentPoint(Point pt, int dir) {
        return KarelWorld.adjacentPoint(pt.x, pt.y, dir);
    }

    public static Point adjacentPoint(int x, int y, int dir) {
        switch (dir) {
            case 2: {
                return new Point(x, y - 1);
            }
            case 3: {
                return new Point(x - 1, y);
            }
            case 0: {
                return new Point(x, y + 1);
            }
            case 1: {
                return new Point(x + 1, y);
            }
        }
        return null;
    }

    public void repaint() {
        if (this.repaintFlag) {
            super.repaint();
        }
    }

    public void update(Graphics g) {
        this.paint(g);
    }

    public void paint(Graphics g) {
        if (this.map == null) {
            return;
        }
        Object object = this.sizeLock;
        synchronized (object) {
            if (this.offscreen == null) {
                Dimension size = this.getSize();
                this.offscreen = this.createImage(size.width, size.height);
            }
            Graphics osg = this.offscreen.getGraphics();
            this.drawEmptyWorld(osg);
            int pass = 0;
            while (pass < 2) {
                int x = 1;
                while (x <= this.cols + 1) {
                    int y = 1;
                    while (y <= this.rows + 1) {
                        boolean mustPaint = false;
                        if (this.getKarelOnSquare(x, y) != null) {
                            mustPaint = true;
                        } else if (this.map[x][y].color != null) {
                            mustPaint = true;
                        } else if (this.map[x][y].nBeepers != 0) {
                            mustPaint = true;
                        } else if (x > 1 && this.map[x][y].wallWest) {
                            mustPaint = true;
                        } else if (y > 1 && this.map[x][y].wallSouth) {
                            mustPaint = true;
                        } else if (x < this.cols && this.map[x + 1][y].wallWest) {
                            mustPaint = true;
                        } else if (y < this.rows && this.map[x][y + 1].wallSouth) {
                            mustPaint = true;
                        }
                        if (mustPaint) {
                            if (pass == 0) {
                                this.updateContents(osg, new Point(x, y));
                            } else {
                                this.updateWalls(osg, new Point(x, y));
                            }
                        }
                        ++y;
                    }
                    ++x;
                }
                ++pass;
            }
            this.drawWorldFrame(osg);
        }
        g.drawImage(this.offscreen, 0, 0, this);
    }

    public void trace() {
        if (this.monitor != null) {
            this.monitor.trace();
        }
    }

    protected void setMonitor(KarelWorldMonitor monitor) {
        this.monitor = monitor;
    }

    protected KarelWorldMonitor getMonitor() {
        return this.monitor;
    }

    protected void componentResizedHook() {
        this.setDisplayParameters(this.cols, this.rows);
        this.repaint();
    }

    protected void mousePressedHook(MouseEvent e) {
        if (this.editMode) {
            this.lastClick = "";
            Point pt = this.getClickCorner(e.getX(), e.getY());
            if (pt == null) {
                this.activeKarel = null;
                this.checkForWallClick(e.getX(), e.getY());
            } else {
                this.activeKarel = this.getKarelOnSquare(pt.x, pt.y);
                if (this.activeKarel == null) {
                    this.checkForCornerClick(pt);
                }
            }
        }
    }

    protected void mouseDraggedHook(MouseEvent e) {
        if (!this.editMode) {
            return;
        }
        if (this.activeKarel != null) {
            Point pt = this.getClickCorner(e.getX(), e.getY());
            if (pt != null && !pt.equals(this.activeKarel.getLocation())) {
                this.activeKarel.setLocation(pt);
                this.repaint();
            }
        } else if (!this.checkForWallClick(e.getX(), e.getY())) {
            this.checkForCornerClick(e.getX(), e.getY());
        }
    }

    private boolean checkForWallClick(int mx, int my) {
        int dir;
        double sx = (double)(mx - this.leftMargin + this.sqSize / 2) / (double)this.sqSize;
        double sy = (double)(this.getSize().height - my - this.bottomMargin - 1 + this.sqSize / 2) / (double)this.sqSize;
        int tx = (int)(sx + 0.5);
        int ty = (int)(sy + 0.5);
        if (Math.abs(Math.abs(sx - (double)tx) - 0.5) <= 0.15 && Math.abs(sy - (double)ty) < 0.3) {
            if ((double)tx > sx) {
                --tx;
            }
            if (tx < 0 || tx > this.cols || ty < 1 || ty > this.rows) {
                return false;
            }
            dir = 1;
        } else if (Math.abs(Math.abs(sy - (double)ty) - 0.5) <= 0.15 && Math.abs(sx - (double)tx) < 0.3) {
            if ((double)ty > sy) {
                --ty;
            }
            if (tx < 1 || tx > this.cols || ty < 0 || ty > this.rows) {
                return false;
            }
            dir = 0;
        } else {
            return false;
        }
        String click = String.valueOf(tx) + "/" + ty + "/" + dir;
        if (!click.equals(this.lastClick)) {
            if (this.monitor != null) {
                this.monitor.wallAction(new Point(tx, ty), dir);
            }
            this.lastClick = click;
        }
        return true;
    }

    private boolean checkForCornerClick(int mx, int my) {
        return this.checkForCornerClick(this.getClickCorner(mx, my));
    }

    private boolean checkForCornerClick(Point pt) {
        if (pt == null) {
            return false;
        }
        String click = String.valueOf(pt.x) + "/" + pt.y;
        if (!click.equals(this.lastClick)) {
            if (this.monitor != null) {
                this.monitor.cornerAction(pt);
            }
            this.lastClick = click;
        }
        return true;
    }

    private Point getClickCorner(int mx, int my) {
        double sx = (double)(mx - this.leftMargin + this.sqSize / 2) / (double)this.sqSize;
        double sy = (double)(this.getSize().height - my - this.bottomMargin - 1 + this.sqSize / 2) / (double)this.sqSize;
        int tx = (int)(sx + 0.5);
        int ty = (int)(sy + 0.5);
        if (tx < 1 || tx > this.cols || ty < 1 || ty > this.rows) {
            return null;
        }
        if (Math.abs(Math.abs(sx - (double)tx) - 0.5) * (double)this.sqSize <= 1.0) {
            return null;
        }
        if (Math.abs(Math.abs(sy - (double)ty) - 0.5) * (double)this.sqSize <= 1.0) {
            return null;
        }
        return new Point(tx, ty);
    }

    private static String encodeColor(Color color) {
        if (color.equals(Color.black)) {
            return "BLACK";
        }
        if (color.equals(Color.blue)) {
            return "BLUE";
        }
        if (color.equals(Color.cyan)) {
            return "CYAN";
        }
        if (color.equals(Color.darkGray)) {
            return "DARK_GRAY";
        }
        if (color.equals(Color.gray)) {
            return "GRAY";
        }
        if (color.equals(Color.green)) {
            return "GREEN";
        }
        if (color.equals(Color.lightGray)) {
            return "LIGHT_GRAY";
        }
        if (color.equals(Color.magenta)) {
            return "MAGENTA";
        }
        if (color.equals(Color.orange)) {
            return "ORANGE";
        }
        if (color.equals(Color.pink)) {
            return "PINK";
        }
        if (color.equals(Color.red)) {
            return "RED";
        }
        if (color.equals(Color.white)) {
            return "WHITE";
        }
        if (color.equals(Color.yellow)) {
            return "YELLOW";
        }
        return "0x" + Integer.toString(color.getRGB() & 16777215).toUpperCase();
    }

    private static Color decodeColor(String name) {
        if (name.equalsIgnoreCase("black")) {
            return Color.black;
        }
        if (name.equalsIgnoreCase("blue")) {
            return Color.blue;
        }
        if (name.equalsIgnoreCase("cyan")) {
            return Color.cyan;
        }
        if (name.equalsIgnoreCase("darkGray") || name.equalsIgnoreCase("DARK_GRAY")) {
            return Color.darkGray;
        }
        if (name.equalsIgnoreCase("gray")) {
            return Color.gray;
        }
        if (name.equalsIgnoreCase("green")) {
            return Color.green;
        }
        if (name.equalsIgnoreCase("lightGray") || name.equalsIgnoreCase("LIGHT_GRAY")) {
            return Color.lightGray;
        }
        if (name.equalsIgnoreCase("magenta")) {
            return Color.magenta;
        }
        if (name.equalsIgnoreCase("orange")) {
            return Color.orange;
        }
        if (name.equalsIgnoreCase("pink")) {
            return Color.pink;
        }
        if (name.equalsIgnoreCase("red")) {
            return Color.red;
        }
        if (name.equalsIgnoreCase("white")) {
            return Color.white;
        }
        if (name.equalsIgnoreCase("yellow")) {
            return Color.yellow;
        }
        return Color.decode(name);
    }

    private void setDisplayParameters(int cols, int rows) {
        this.offscreen = null;
        int usableWidth = this.getSize().width - (this.numberSquaresFlag ? 16 : 2);
        int usableHeight = this.getSize().height - (this.numberSquaresFlag ? 15 : 0) - 2;
        this.width = cols;
        this.height = rows;
        this.sqSize = this.forcedSize == 0 ? Math.min(usableWidth / cols, usableHeight / rows) : this.forcedSize;
        this.width = cols * this.sqSize;
        this.height = rows * this.sqSize;
        switch (this.alignment) {
            case 3: 
            case 11: 
            case 13: {
                this.leftMargin = this.numberSquaresFlag ? 16 : 2;
                break;
            }
            case 0: 
            case 2: 
            case 14: {
                this.leftMargin = (this.numberSquaresFlag ? 16 : 2) + (usableWidth - this.width) / 2;
                break;
            }
            case 1: 
            case 10: 
            case 12: {
                this.leftMargin = this.getSize().width - this.width - 1;
            }
        }
        switch (this.alignment) {
            case 0: 
            case 10: 
            case 11: {
                this.bottomMargin = this.getSize().height - this.height - 2;
                break;
            }
            case 1: 
            case 3: 
            case 14: {
                this.bottomMargin = (this.numberSquaresFlag ? 15 : 0) + (usableHeight - this.height) / 2;
                break;
            }
            case 2: 
            case 12: 
            case 13: {
                this.bottomMargin = this.numberSquaresFlag ? 15 : 0;
            }
        }
    }

    private void drawEmptyWorld(Graphics g) {
        if (g == null) {
            return;
        }
        Dimension size = this.getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.black);
        int x = this.leftMargin + this.sqSize / 2;
        int ix = 1;
        while (ix <= this.cols) {
            int y = this.getSize().height - this.bottomMargin - (this.sqSize + 1) / 2 - 1;
            int iy = 1;
            while (iy <= this.rows) {
                this.drawCornerMarker(g, x, y);
                y -= this.sqSize;
                ++iy;
            }
            x += this.sqSize;
            ++ix;
        }
    }

    private void drawWorldFrame(Graphics g) {
        if (g == null) {
            return;
        }
        g.setColor(Color.black);
        int x = this.leftMargin;
        int y = this.getSize().height - this.bottomMargin - 1 - this.height;
        if (this.sqSize >= 24) {
            g.drawRect(x, y, this.width - 1, this.height - 1);
            g.drawRect(x - 1, y - 1, this.width + 1, this.height + 1);
        } else {
            g.drawRect(x - 1, y - 1, this.width, this.height);
        }
        if (this.sqSize > 15 && this.numberSquaresFlag) {
            g.setFont(NUMBER_FONT);
            FontMetrics fm = g.getFontMetrics();
            x = this.leftMargin + this.sqSize / 2;
            y = this.getSize().height - this.bottomMargin + 10;
            int ix = 1;
            while (ix <= this.cols) {
                String label = "" + ix;
                g.drawString(label, x - fm.stringWidth(label) / 2, y);
                x += this.sqSize;
                ++ix;
            }
            x = this.leftMargin - 3;
            y = this.getSize().height - this.bottomMargin - this.sqSize / 2 + 2;
            int iy = 1;
            while (iy <= this.rows) {
                g.drawString("" + iy, x - g.getFontMetrics().stringWidth("" + iy), y);
                y -= this.sqSize;
                ++iy;
            }
        }
    }

    private void updateCorner(Graphics g, Point pt) {
        this.updateContents(g, pt);
        this.updateWalls(g, pt);
    }

    public void updateContents(Graphics g, Point pt) {
        if (g == null) {
            return;
        }
        if (this.outOfBounds(pt)) {
            return;
        }
        int x = this.leftMargin + (pt.x - 1) * this.sqSize;
        int y = this.getSize().height - this.bottomMargin - 1 - pt.y * this.sqSize;
        this.drawCorner(g, x, y, pt);
    }

    public void drawCorner(Graphics g, int x, int y, Point pt) {
        Karel karel;
        if (g == null) {
            return;
        }
        int sqSize = this.getSquareSize();
        Color color = this.getCornerColor(pt);
        g.setColor(color == null ? Color.white : color);
        g.fillRect(x, y, sqSize, sqSize);
        int cx = x + sqSize / 2;
        int cy = y + sqSize / 2;
        int nBeepers = this.getBeepersOnCorner(pt);
        if (nBeepers > 0) {
            if (nBeepers == 1) {
                nBeepers = -3;
            }
            this.drawBeeperForStyle(g, cx, cy, sqSize, nBeepers, 1);
        }
        if ((karel = this.getKarelOnSquare(pt.x, pt.y)) != null) {
            this.drawKarel(g, cx, cy, karel.getDirection(), sqSize);
        } else if (color == null && nBeepers == 0) {
            this.drawCornerMarker(g, cx, cy);
        }
    }

    public static void drawMarkedCorner(Graphics g, int x, int y, int size) {
        if (g == null) {
            return;
        }
        int inset = Math.max(2, size / 5);
        g.setColor(Color.white);
        g.fillRect(x, y, size, size);
        g.setColor(MARKED_COLOR);
        g.fillRect(x + inset, y + inset, size - 2 * inset, size - 2 * inset);
    }

    public void drawKarel(Graphics g, int x, int y, int dir, int size) {
        if (g == null) {
            return;
        }
        if (size < 20 || this.getLook() == 0) {
            this.drawSimpleKarel(g, x, y, dir, size);
        } else {
            this.drawFancyKarel(g, x, y, dir, size);
        }
    }

    public void drawSimpleKarel(Graphics g, int x, int y, int dir, int size) {
        if (g == null) {
            return;
        }
        if ((size = (int)Math.round((double)size * 0.7)) % 2 == 0) {
            --size;
        }
        int half = (size + 1) / 2;
        int pass = 1;
        while (pass <= 2) {
            KarelRegion r = new KarelRegion();
            r.setOrigin(x, y, - half, - half, dir);
            r.addVector(half, 0.0, dir);
            r.addVector(half, half, dir);
            r.addVector(- half, half, dir);
            r.addVector(- half, 0.0, dir);
            r.addVector(0.0, - size, dir);
            if (pass == 1) {
                g.setColor(Color.white);
                g.fillPolygon(r.getPolygon());
            } else {
                g.setColor(Color.black);
                g.drawPolygon(r.getPolygon());
            }
            ++pass;
        }
    }

    public void drawFancyKarel(Graphics g, int x, int y, int dir, int size) {
        KarelWorld.drawFancyKarel(g, x, y, dir, size - 6, Color.white);
    }

    public static void drawFancyKarel(Graphics g, int x, int y, int dir, int size, Color color) {
        if (g == null) {
            return;
        }
        int pass = 1;
        while (pass <= 2) {
            KarelRegion r = new KarelRegion();
            r.setOrigin(x, y, -0.2 * (double)size, -0.33 * (double)size + 0.1 * (double)size, dir);
            int sx = r.getCurrentX();
            int sy = r.getCurrentY();
            g.setColor(pass == 1 ? color : Color.black);
            r.addVector(0.0, 0.8 * (double)size - 0.1 * (double)size, dir);
            r.addVector(0.6 * (double)size - 0.15 * (double)size, 0.0, dir);
            r.addVector(0.15 * (double)size, -0.15 * (double)size, dir);
            r.addVector(0.0, - 0.8 * (double)size - 0.15 * (double)size, dir);
            r.addVector(- 0.6 * (double)size - 0.1 * (double)size, 0.0, dir);
            r.addVector(-0.1 * (double)size, 0.1 * (double)size, dir);
            if (pass == 1) {
                r.getPolygon().addPoint(sx, sy);
                r.addVector(0.13 * (double)size, 0.18000000000000002 * (double)size, dir);
            } else {
                g.drawPolygon(r.getPolygon());
                r = new KarelRegion();
                r.setOrigin(sx, sy, 0.13 * (double)size, 0.18000000000000002 * (double)size, dir);
            }
            r.addVector(0.3 * (double)size, 0.0, dir);
            r.addVector(0.0, 0.4 * (double)size, dir);
            r.addVector(-0.3 * (double)size, 0.0, dir);
            r.addVector(0.0, -0.4 * (double)size, dir);
            if (pass == 1) {
                r.getPolygon().addPoint(sx, sy);
                g.fillPolygon(r.getPolygon());
                r = new KarelRegion();
                r.setOrigin(sx, sy, 0.13 * (double)size - 1.0, 0.18000000000000002 * (double)size - 1.0, dir);
                r.addVector(0.3 * (double)size + 2.0, 0.0, dir);
                r.addVector(0.0, 0.4 * (double)size + 2.0, dir);
                r.addVector(- 0.3 * (double)size + 2.0, 0.0, dir);
                r.addVector(0.0, - 0.4 * (double)size + 2.0, dir);
                g.drawPolygon(r.getPolygon());
            } else {
                g.drawPolygon(r.getPolygon());
            }
            ++pass;
        }
        g.setColor(Color.black);
        KarelRegion r = new KarelRegion();
        r.setOrigin(x, y, -0.07 * (double)size + 0.3 * (double)size, (-0.05 * (double)size + -0.33 * (double)size) / 2.0, dir);
        r.addVector(-0.15 * (double)size, 0.0, dir);
        g.drawPolygon(r.getPolygon());
        r = new KarelRegion();
        r.setOrigin(x, y, -0.2 * (double)size, -0.05 * (double)size, dir);
        r.addVector(- 0.08 * (double)size + 0.08 * (double)size, 0.0, dir);
        r.addVector(0.0, -0.2 * (double)size, dir);
        r.addVector(0.08 * (double)size, 0.0, dir);
        r.addVector(0.0, 0.2 * (double)size - 0.08 * (double)size, dir);
        r.addVector(0.08 * (double)size, 0.0, dir);
        r.addVector(0.0, 0.08 * (double)size, dir);
        g.fillPolygon(r.getPolygon());
        g.drawPolygon(r.getPolygon());
        r = new KarelRegion();
        r.setOrigin(x, y, -0.07 * (double)size + 0.3 * (double)size - 0.15 * (double)size, -0.33 * (double)size, dir);
        r.addVector(0.0, - 0.08 * (double)size + 0.08 * (double)size, dir);
        r.addVector(0.2 * (double)size, 0.0, dir);
        r.addVector(0.0, 0.08 * (double)size, dir);
        r.addVector(- 0.2 * (double)size - 0.08 * (double)size, 0.0, dir);
        r.addVector(0.0, 0.08 * (double)size, dir);
        r.addVector(-0.08 * (double)size, 0.0, dir);
        g.fillPolygon(r.getPolygon());
        g.drawPolygon(r.getPolygon());
    }

    public void drawBeeperForStyle(Graphics g, int x, int y, int size, int n, int border) {
        KarelWorld.drawBeeper(g, x, y, size, n, border, this);
    }

    public static void drawBeeper(Graphics g, int x, int y, int size, int n, int border, Component comp) {
        if (g == null) {
            return;
        }
        int beeperSize = (int)Math.round((double)size * 0.7);
        if (beeperSize % 2 == 0) {
            --beeperSize;
        }
        int half = (beeperSize + 1) / 2;
        KarelRegion r = new KarelRegion();
        r.setOrigin(x, y, 0.0, - half, 1);
        r.addVector(half, half, 1);
        r.addVector(- half, half, 1);
        r.addVector(- half, - half, 1);
        r.addVector(half, - half, 1);
        g.setColor(BEEPER_COLOR);
        g.fillPolygon(r.getPolygon());
        g.drawPolygon(r.getPolygon());
        g.setColor(Color.black);
        int i = 0;
        while (i < border) {
            int delta = half + i;
            g.drawLine(x - delta, y, x, y + delta);
            g.drawLine(x, y + delta, x + delta, y);
            g.drawLine(x + delta, y, x, y - delta);
            g.drawLine(x, y - delta, x - delta, y);
            ++i;
        }
        if (size > 15 && n != 1) {
            KarelWorld.labelBeeper(g, x, y, size, KarelWorld.beeperLabel(n), comp);
        }
    }

    public static void labelBeeper(Graphics g, int x, int y, int size, String label, Component comp) {
        if (label.equals("\u221e")) {
            if (infinityImage == null) {
                infinityImage = MediaTools.createImage(INFINITY);
            }
            g.drawImage(infinityImage, x - 4, y - 2, comp);
        } else {
            int psz = 7;
            switch (label.length()) {
                case 1: 
                case 2: {
                    psz = 10;
                    break;
                }
                default: {
                    psz = 7;
                }
            }
            Font font = new Font("Times", 0, psz);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            g.drawString(label, x - fm.stringWidth(label) / 2, y + fm.getAscent() / 2);
        }
    }

    public static String beeperLabel(int n) {
        switch (n) {
            case 99999999: {
                return "\u221e";
            }
            case -1: {
                return "+1";
            }
            case -2: {
                return "-1";
            }
            case -3: {
                return "";
            }
        }
        return "" + n;
    }

    public void updateWalls(Graphics g, Point pt) {
        if (g == null) {
            return;
        }
        if (this.outOfBounds(pt)) {
            return;
        }
        int x = this.leftMargin + (pt.x - 1) * this.sqSize;
        int y = this.getSize().height - this.bottomMargin - 1 - pt.y * this.sqSize;
        g.setColor(Color.black);
        int dir = 0;
        while (dir <= 3) {
            if (this.checkWall(pt, dir)) {
                this.drawWall(g, x, y, dir);
            }
            if (this.sqSize < 24) {
                this.fixCornerPoint(g, pt, dir);
            }
            ++dir;
        }
    }

    private void drawWall(Graphics g, int x, int y, int dir) {
        int y0;
        int x1;
        int y1;
        int x0;
        if (g == null) {
            return;
        }
        switch (dir) {
            case 0: {
                x0 = x;
                y0 = y;
                x1 = x0 + this.sqSize;
                y1 = y0;
                break;
            }
            case 1: {
                x0 = x + this.sqSize;
                y0 = y;
                x1 = x0;
                y1 = y0 + this.sqSize;
                break;
            }
            case 2: {
                x0 = x;
                y0 = y + this.sqSize;
                x1 = x0 + this.sqSize;
                y1 = y0;
                break;
            }
            case 3: {
                x0 = x;
                y0 = y;
                x1 = x0;
                y1 = y0 + this.sqSize;
                break;
            }
            default: {
                y1 = 0;
                x1 = 0;
                y0 = 0;
                x0 = 0;
            }
        }
        if (this.sqSize < 24) {
            g.drawLine(x0 - 1, y0 - 1, x1 - 1, y1 - 1);
        } else if (x0 == x1) {
            g.drawLine(x0 - 1, y0 - 1, x1 - 1, y1);
            g.drawLine(x0, y0 - 1, x1, y1);
        } else {
            g.drawLine(x0 - 1, y0 - 1, x1, y1 - 1);
            g.drawLine(x0 - 1, y0, x1, y1);
        }
    }

    public void drawCornerMarker(Graphics g, int x, int y) {
        if (g == null) {
            return;
        }
        g.setColor(Color.black);
        if (this.sqSize < 11) {
            g.drawLine(x, y, x, y);
        } else {
            g.drawLine(x - 1, y, x + 1, y);
            g.drawLine(x, y - 1, x, y + 1);
        }
    }

    private void fixCornerPoint(Graphics g, Point pt, int dir) {
        int left = KarelWorld.leftFrom(dir);
        Point pUp = KarelWorld.adjacentPoint(pt, dir);
        Point pLeft = KarelWorld.adjacentPoint(pt, left);
        if (!this.outOfBounds(pUp) && this.checkWall(pUp, left)) {
            int x = this.leftMargin + (pUp.x - 1) * this.sqSize;
            int y = this.getSize().height - this.bottomMargin - 1 - pUp.y * this.sqSize;
            this.drawWall(g, x, y, left);
        } else if (!this.outOfBounds(pLeft) && this.checkWall(pLeft, dir)) {
            int x = this.leftMargin + (pLeft.x - 1) * this.sqSize;
            int y = this.getSize().height - this.bottomMargin - 1 - pLeft.y * this.sqSize;
            this.drawWall(g, x, y, dir);
        }
    }

    private Rectangle getCornerRect(Point pt) {
        int x = this.leftMargin + (pt.x - 1) * this.sqSize;
        int y = this.getSize().height - this.bottomMargin - 1 - pt.y * this.sqSize;
        return new Rectangle(x - 1, y - 1, this.sqSize + 2, this.sqSize + 2);
    }

    public void save() {
        if (this.pathname == null) {
            return;
        }
        Point pt = new Point(0, 0);
        try {
            PrintWriter wr = new PrintWriter(new FileWriter(this.pathname));
            wr.println("Dimension: (" + this.cols + ", " + this.rows + ")");
            pt.x = 1;
            while (pt.x <= this.cols) {
                pt.y = 1;
                while (pt.y <= this.rows) {
                    if (pt.x > 1 && this.checkWall(pt, 3)) {
                        wr.println("Wall: (" + pt.x + ", " + pt.y + ") west");
                    }
                    if (pt.y > 1 && this.checkWall(pt, 2)) {
                        wr.println("Wall: (" + pt.x + ", " + pt.y + ") south");
                    }
                    ++pt.y;
                }
                ++pt.x;
            }
            pt.x = 1;
            while (pt.x <= this.cols) {
                pt.y = 1;
                while (pt.y <= this.rows) {
                    int nBeepers;
                    Color color = this.getCornerColor(pt);
                    if (color != null) {
                        wr.println("Color: (" + pt.x + ", " + pt.y + ") " + KarelWorld.encodeColor(color));
                    }
                    if ((nBeepers = this.getBeepersOnCorner(pt)) != 0) {
                        String str = nBeepers == 99999999 ? "INFINITE" : "" + nBeepers;
                        wr.println("Beeper: (" + pt.x + ", " + pt.y + ") " + str);
                    }
                    ++pt.y;
                }
                ++pt.x;
            }
            Enumeration e = this.karels.elements();
            while (e.hasMoreElements()) {
                String str;
                Karel karel = (Karel)e.nextElement();
                String dirName = "Error";
                switch (karel.getDirection()) {
                    case 0: {
                        dirName = "north";
                        break;
                    }
                    case 1: {
                        dirName = "east";
                        break;
                    }
                    case 2: {
                        dirName = "south";
                        break;
                    }
                    case 3: {
                        dirName = "west";
                    }
                }
                Point loc = karel.getLocation();
                wr.print("Karel: (" + loc.x + ", " + loc.y + ") " + dirName);
                int nBeepers = karel.getBeepersInBag();
                if (nBeepers == 0) continue;
                String string = str = nBeepers == 99999999 ? "INFINITE" : "" + nBeepers;
                if (this.getKarelCount() == 1) {
                    wr.println();
                    wr.println("BeeperBag: " + str);
                    continue;
                }
                wr.println(" " + str);
            }
            if (this.monitor != null) {
                wr.println("Speed: " + this.speedFormat.format(this.monitor.getSpeed()));
            }
            wr.close();
        }
        catch (IOException ex) {
            throw new ErrorException((String)((Object)ex));
        }
        Platform.setFileTypeAndCreator(this.pathname, "TEXT", "CWIE");
    }

    public void load(String[] lines) {
        String program = "";
        int i = 0;
        while (i < lines.length) {
            program = String.valueOf(program) + lines[i] + '\n';
            ++i;
        }
        this.load(new StringReader(program));
    }

    public void load(File file) {
        try {
            this.pathname = file.getPath();
            FileReader rd = new FileReader(file);
            if (rd == null) {
                throw new ErrorException("Can't open " + this.pathname);
            }
            this.load(rd);
            rd.close();
        }
        catch (IOException ex) {
            throw new ErrorException("I/O error reading map file");
        }
    }

    public void load(String pathname) {
        try {
            this.pathname = pathname;
            FileReader rd = new FileReader(pathname);
            if (rd == null) {
                throw new ErrorException("Can't open " + pathname);
            }
            this.load(rd);
            rd.close();
        }
        catch (IOException ex) {
            throw new ErrorException("I/O error reading map file");
        }
    }

    public void load(Reader rd) {
        try {
            this.setRepaintFlag(false);
            this.lastBeeperCount = 99999999;
            this.tokenizer = new StreamTokenizer(rd);
            this.tokenizer.eolIsSignificant(true);
            this.tokenizer.lowerCaseMode(true);
            this.tokenizer.resetSyntax();
            this.tokenizer.wordChars(65, 90);
            this.tokenizer.wordChars(97, 122);
            this.tokenizer.wordChars(48, 57);
            this.tokenizer.wordChars(46, 46);
            this.tokenizer.wordChars(95, 95);
            this.tokenizer.whitespaceChars(32, 32);
            this.tokenizer.whitespaceChars(9, 9);
            this.tokenizer.whitespaceChars(13, 13);
            while (this.readMapLine()) {
            }
            rd.close();
            this.setRepaintFlag(true);
            this.repaint();
        }
        catch (IOException ex) {
            this.setRepaintFlag(true);
            throw new ErrorException("I/O error reading map file");
        }
    }

    private boolean readMapLine() {
        int token = this.nextToken();
        switch (token) {
            case -1: {
                return false;
            }
            case 10: {
                return true;
            }
            case -3: {
                String cmd = this.tokenizer.sval;
                if (this.nextToken() != 58) {
                    throw new ErrorException("Missing colon after " + cmd);
                }
                if (cmd.equals("dimension")) {
                    this.dimensionCommand();
                    break;
                }
                if (cmd.equals("karel") || cmd.equals("turtle")) {
                    this.karelCommand();
                    break;
                }
                if (cmd.equals("wall")) {
                    this.wallCommand();
                    break;
                }
                if (cmd.equals("mark") || cmd.equals("color")) {
                    this.setColorCommand();
                    break;
                }
                if (cmd.equals("speed")) {
                    this.speedCommand();
                    break;
                }
                if (cmd.equals("beeper")) {
                    this.beeperCommand();
                    break;
                }
                if (cmd.equals("beeperbag")) {
                    this.beeperBagCommand();
                    break;
                }
                throw new ErrorException("Illegal command: " + cmd);
            }
            default: {
                throw new ErrorException("Illegal character '" + (char)token + "'");
            }
        }
        return true;
    }

    private void dimensionCommand() {
        this.verifyToken(40);
        int cols = this.scanInt();
        this.verifyToken(44);
        int rows = this.scanInt();
        this.verifyToken(41);
        this.verifyToken(10);
        this.init(cols, rows);
    }

    private void karelCommand() {
        Point pt = new Point(0, 0);
        int dir = 1;
        int nBeepers = this.lastBeeperCount;
        this.verifyToken(40);
        pt.x = this.scanInt();
        this.verifyToken(44);
        pt.y = this.scanInt();
        this.verifyToken(41);
        if (this.nextToken() != -3) {
            throw new ErrorException("Illegal direction");
        }
        if ("north".startsWith(this.tokenizer.sval)) {
            dir = 0;
        } else if ("east".startsWith(this.tokenizer.sval)) {
            dir = 1;
        } else if ("south".startsWith(this.tokenizer.sval)) {
            dir = 2;
        } else if ("west".startsWith(this.tokenizer.sval)) {
            dir = 3;
        } else {
            throw new ErrorException("Illegal direction " + this.tokenizer.sval);
        }
        int token = this.nextToken();
        if (token == -3) {
            if ("infinite".startsWith(this.tokenizer.sval) || "infinity".startsWith(this.tokenizer.sval)) {
                nBeepers = 99999999;
            } else {
                try {
                    nBeepers = Integer.parseInt(this.tokenizer.sval);
                }
                catch (NumberFormatException ex) {
                    throw new ErrorException("Illegal beeper bag value");
                }
            }
            token = this.nextToken();
        }
        if (token != 10) {
            throw new ErrorException("Unexpected tokens at end of line");
        }
        this.lastKarel = this.getKarel();
        if (this.lastKarel != null) {
            this.lastKarel.setLocation(pt.x, pt.y);
            this.lastKarel.setDirection(dir);
            this.lastKarel.setBeepersInBag(nBeepers);
        }
    }

    private void wallCommand() {
        Point pt = new Point(0, 0);
        int dir = 1;
        this.verifyToken(40);
        pt.x = this.scanInt();
        this.verifyToken(44);
        pt.y = this.scanInt();
        this.verifyToken(41);
        if (this.nextToken() != -3) {
            throw new ErrorException("Illegal direction");
        }
        if ("north".startsWith(this.tokenizer.sval)) {
            dir = 0;
        } else if ("east".startsWith(this.tokenizer.sval)) {
            dir = 1;
        } else if ("south".startsWith(this.tokenizer.sval)) {
            dir = 2;
        } else if ("west".startsWith(this.tokenizer.sval)) {
            dir = 3;
        } else {
            throw new ErrorException("Illegal direction " + this.tokenizer.sval);
        }
        this.verifyToken(10);
        this.setWall(pt, dir);
    }

    private void setColorCommand() {
        Point pt = new Point(0, 0);
        String colorName = null;
        this.verifyToken(40);
        pt.x = this.scanInt();
        this.verifyToken(44);
        pt.y = this.scanInt();
        this.verifyToken(41);
        int tt = this.nextToken();
        if (tt != 10) {
            if (tt != -3) {
                throw new ErrorException("Missing color name");
            }
            colorName = this.tokenizer.sval.toLowerCase();
            this.verifyToken(10);
        }
        this.setCornerColor(pt, KarelWorld.decodeColor(colorName));
    }

    private void speedCommand() {
        if (this.nextToken() != -3) {
            throw new ErrorException("I expected a number");
        }
        double speed = new Double(this.tokenizer.sval);
        this.verifyToken(10);
        if (this.monitor != null) {
            this.monitor.setSpeed(speed);
        }
    }

    protected void beeperBagCommand() {
        if (this.nextToken() != -3) {
            throw new ErrorException("Illegal beeper count");
        }
        int nBeepers = 0;
        if ("infinite".startsWith(this.tokenizer.sval) || "infinity".startsWith(this.tokenizer.sval)) {
            nBeepers = 99999999;
        } else {
            this.tokenizer.pushBack();
            nBeepers = this.scanInt();
        }
        this.verifyToken(10);
        if (this.lastKarel == null) {
            this.lastBeeperCount = nBeepers;
        } else {
            this.lastKarel.setBeepersInBag(nBeepers);
        }
    }

    protected void beeperCommand() {
        Point pt = new Point(0, 0);
        boolean dir = true;
        this.verifyToken(40);
        pt.x = this.scanInt();
        this.verifyToken(44);
        pt.y = this.scanInt();
        this.verifyToken(41);
        int nBeepers = 1;
        int token = this.nextToken();
        if (token != 10) {
            if (token != -3) {
                throw new ErrorException("Illegal beeper count");
            }
            if ("infinite".startsWith(this.tokenizer.sval) || "infinity".startsWith(this.tokenizer.sval)) {
                nBeepers = 99999999;
            } else {
                this.tokenizer.pushBack();
                nBeepers = this.scanInt();
            }
            this.verifyToken(10);
        }
        this.setBeepersOnCorner(pt, nBeepers);
    }

    protected void ignoreCommand() {
        while (this.nextToken() != 10) {
        }
    }

    private void verifyToken(int token) {
        if (this.nextToken() != token) {
            if (token == 10) {
                throw new ErrorException("Unexpected tokens at end of line");
            }
            throw new ErrorException("I expected a '" + (char)token + "'");
        }
    }

    private int scanInt() {
        if (this.nextToken() != -3) {
            throw new ErrorException("I expected an integer");
        }
        try {
            return Integer.parseInt(this.tokenizer.sval);
        }
        catch (NumberFormatException ex) {
            throw new ErrorException("Illegal integer");
        }
    }

    private int nextToken() {
        int token = 0;
        try {
            token = this.tokenizer.nextToken();
        }
        catch (IOException ex) {
            throw new ErrorException("Exception: " + ex);
        }
        return token == 10 ? 10 : token;
    }
}


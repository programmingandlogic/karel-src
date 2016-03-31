/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Hashtable;
import stanford.karel.OptionTable;

class HVLayout
implements LayoutManager {
    public static final int DEFAULT_SPACE = 5;
    public static final int HORIZONTAL = 2;
    public static final int VERTICAL = 3;
    private static final int MINIMUM = 0;
    private static final int PREFERRED = 1;
    private static final int CENTER = 10;
    private static final int NORTH = 11;
    private static final int NORTHEAST = 12;
    private static final int EAST = 13;
    private static final int SOUTHEAST = 14;
    private static final int SOUTH = 15;
    private static final int SOUTHWEST = 16;
    private static final int WEST = 17;
    private static final int NORTHWEST = 18;
    private static final int NONE = 0;
    private static final int BOTH = 1;
    private int orientation;
    private Hashtable constraintTable;

    public HVLayout(int orientation) {
        this.orientation = orientation;
        this.constraintTable = new Hashtable();
    }

    public void addLayoutComponent(String constraints, Component comp) {
        Object object = comp.getTreeLock();
        synchronized (object) {
            this.constraintTable.put(comp, new OptionTable(constraints.toLowerCase()));
        }
    }

    public void removeLayoutComponent(Component comp) {
        this.constraintTable.remove(comp);
    }

    public Dimension preferredLayoutSize(Container parent) {
        Object object = parent.getTreeLock();
        synchronized (object) {
            return this.getContainerSize(parent, 1);
        }
    }

    public Dimension minimumLayoutSize(Container parent) {
        Object object = parent.getTreeLock();
        synchronized (object) {
            return this.getContainerSize(parent, 0);
        }
    }

    public void layoutContainer(Container parent) {
        Object object = parent.getTreeLock();
        synchronized (object) {
            int nComponents = parent.getComponentCount();
            Dimension psize = parent.getSize();
            Dimension tsize = this.preferredLayoutSize(parent);
            int nStretch = this.getStretchCount(parent);
            int extra = this.getExtraSpace(psize, tsize, nStretch);
            Point origin = new Point(0, 0);
            if (nStretch == 0) {
                origin = this.getInitialOrigin(parent, psize, tsize);
            }
            int i = 0;
            while (i < nComponents) {
                Component comp = parent.getComponent(i);
                OptionTable options = (OptionTable)this.constraintTable.get(comp);
                Dimension csize = this.getLayoutSize(comp, options, 1);
                Dimension lsize = this.applyStretching(this.getStretchOption(options), csize, psize, extra);
                Dimension vsize = this.applyStretching(1, csize, psize, extra);
                Rectangle bounds = this.getLayoutBounds(options, lsize, vsize, origin);
                comp.setBounds(bounds);
                if (this.orientation == 2) {
                    origin.x += lsize.width;
                } else {
                    origin.y += lsize.height;
                }
                ++i;
            }
        }
    }

    private Dimension getContainerSize(Container parent, int type) {
        Dimension result = new Dimension(0, 0);
        int nComponents = parent.getComponentCount();
        int i = 0;
        while (i < nComponents) {
            Component comp = parent.getComponent(i);
            OptionTable options = (OptionTable)this.constraintTable.get(comp);
            Dimension size = this.getLayoutSize(comp, options, type);
            if (this.orientation == 2) {
                result.width += size.width;
                result.height = Math.max(result.height, size.height);
            } else {
                result.width = Math.max(result.width, size.width);
                result.height += size.height;
            }
            ++i;
        }
        return result;
    }

    private Dimension getLayoutSize(Component comp, OptionTable options, int type) {
        Dimension size = new Dimension(0, 0);
        if (type == 1) {
            size = new Dimension(comp.getPreferredSize());
        }
        size.width = options.getIntOption("width", size.width);
        size.height = options.getIntOption("height", size.height);
        size = this.limitSize(size, comp);
        Insets insets = this.getInsetOption(options);
        size.width += insets.left + insets.right;
        size.height += insets.top + insets.bottom;
        return size;
    }

    private Dimension limitSize(Dimension size, Component comp) {
        Dimension minSize = comp.getMinimumSize();
        Dimension maxSize = comp.getMaximumSize();
        int width = Math.max(minSize.width, Math.min(size.width, maxSize.width));
        int height = Math.max(minSize.height, Math.min(size.height, maxSize.height));
        return new Dimension(width, height);
    }

    private Insets getInsetOption(OptionTable options) {
        Insets insets = new Insets(0, 0, 0, 0);
        if (options.isSpecified("space")) {
            switch (this.orientation) {
                case 2: {
                    insets.left = options.getIntOption("space");
                    break;
                }
                case 3: {
                    insets.top = options.getIntOption("space");
                }
            }
        }
        if (options.isSpecified("left")) {
            insets.left = options.getIntOption("left");
        }
        if (options.isSpecified("right")) {
            insets.right = options.getIntOption("right");
        }
        if (options.isSpecified("top")) {
            insets.top = options.getIntOption("top");
        }
        if (options.isSpecified("bottom")) {
            insets.bottom = options.getIntOption("bottom");
        }
        return insets;
    }

    private int getStretchOption(OptionTable options) {
        if (options.isSpecified("fill")) {
            return this.orientation;
        }
        if (options.isSpecified("stretch")) {
            String value = options.getOption("stretch", "both");
            if (value.equals("none")) {
                return 0;
            }
            if (value.equals("horizontal")) {
                return 2;
            }
            if (value.equals("vertical")) {
                return 3;
            }
            if (value.equals("both")) {
                return 1;
            }
        }
        return 0;
    }

    private int getAnchorOption(OptionTable options) {
        int anchor = 10;
        if (options.isSpecified("anchor")) {
            String value = options.getOption("anchor", "northwest");
            if (value.equals("center")) {
                anchor = 10;
            } else if (value.equals("north")) {
                anchor = 11;
            } else if (value.equals("northeast") || value.equals("ne")) {
                anchor = 12;
            } else if (value.equals("east")) {
                anchor = 13;
            } else if (value.equals("southeast") || value.equals("se")) {
                anchor = 14;
            } else if (value.equals("south")) {
                anchor = 15;
            } else if (value.equals("southwest") || value.equals("sw")) {
                anchor = 16;
            } else if (value.equals("west")) {
                anchor = 17;
            } else if (value.equals("northwest") || value.equals("nw")) {
                anchor = 18;
            }
        } else if (options.isSpecified("center")) {
            anchor = 10;
        } else if (options.isSpecified("north")) {
            anchor = 11;
        } else if (options.isSpecified("northeast") || options.isSpecified("ne")) {
            anchor = 12;
        } else if (options.isSpecified("east")) {
            anchor = 13;
        } else if (options.isSpecified("southeast") || options.isSpecified("se")) {
            anchor = 14;
        } else if (options.isSpecified("south")) {
            anchor = 15;
        } else if (options.isSpecified("southwest") || options.isSpecified("sw")) {
            anchor = 16;
        } else if (options.isSpecified("west")) {
            anchor = 17;
        } else if (options.isSpecified("northwest") || options.isSpecified("nw")) {
            anchor = 18;
        }
        return anchor;
    }

    private int getStretchCount(Container parent) {
        int nComponents = parent.getComponentCount();
        int nStretch = 0;
        int i = 0;
        while (i < nComponents) {
            Component comp = parent.getComponent(i);
            OptionTable options = (OptionTable)this.constraintTable.get(comp);
            int stretch = this.getStretchOption(options);
            if (this.orientation == 2) {
                if (stretch == 2 || stretch == 1) {
                    ++nStretch;
                }
            } else if (stretch == 3 || stretch == 1) {
                ++nStretch;
            }
            ++i;
        }
        return nStretch;
    }

    private int getExtraSpace(Dimension psize, Dimension tsize, int nStretch) {
        if (nStretch == 0) {
            return 0;
        }
        if (this.orientation == 2) {
            return (psize.width - tsize.width) / nStretch;
        }
        return (psize.height - tsize.height) / nStretch;
    }

    private Dimension applyStretching(int stretch, Dimension csize, Dimension psize, int extra) {
        int width = csize.width;
        int height = csize.height;
        if (stretch == 2 || stretch == 1) {
            width = this.orientation == 2 ? (width += extra) : psize.width;
        }
        if (stretch == 3 || stretch == 1) {
            height = this.orientation == 3 ? (height += extra) : psize.height;
        }
        return new Dimension(width, height);
    }

    private Rectangle getLayoutBounds(OptionTable options, Dimension lsize, Dimension vsize, Point origin) {
        Insets insets = this.getInsetOption(options);
        int anchor = this.getAnchorOption(options);
        int dx = insets.left;
        int dy = insets.top;
        int width = lsize.width - insets.left - insets.right;
        int height = lsize.height - insets.top - insets.bottom;
        if (this.orientation == 2) {
            switch (anchor) {
                case 11: 
                case 12: 
                case 18: {
                    dy = insets.top;
                    break;
                }
                case 10: 
                case 13: 
                case 17: {
                    dy = insets.top + (vsize.height - lsize.height) / 2;
                    break;
                }
                case 14: 
                case 15: 
                case 16: {
                    dy = vsize.height - insets.bottom - lsize.height;
                }
                default: {
                    break;
                }
            }
        } else {
            switch (anchor) {
                case 16: 
                case 17: 
                case 18: {
                    dx = insets.left;
                    break;
                }
                case 10: 
                case 11: 
                case 15: {
                    dx = insets.left + (vsize.width - lsize.width) / 2;
                    break;
                }
                case 12: 
                case 13: 
                case 14: {
                    dx = vsize.width - insets.right - lsize.width;
                }
            }
        }
        return new Rectangle(origin.x + dx, origin.y + dy, width, height);
    }

    private Point getInitialOrigin(Container parent, Dimension psize, Dimension tsize) {
        int x = 0;
        int y = 0;
        if (parent.getComponentCount() > 0) {
            OptionTable options = (OptionTable)this.constraintTable.get(parent.getComponent(0));
            int anchor = this.getAnchorOption(options);
            if (this.orientation == 2) {
                switch (anchor) {
                    case 16: 
                    case 17: 
                    case 18: {
                        x = 0;
                        break;
                    }
                    case 10: 
                    case 11: 
                    case 15: {
                        x = (psize.width - tsize.width) / 2;
                        break;
                    }
                    case 12: 
                    case 13: 
                    case 14: {
                        x = psize.width - tsize.width;
                    }
                    default: {
                        break;
                    }
                }
            } else {
                switch (anchor) {
                    case 11: 
                    case 12: 
                    case 18: {
                        y = 0;
                        break;
                    }
                    case 10: 
                    case 13: 
                    case 17: {
                        y = (psize.height - tsize.height) / 2;
                        break;
                    }
                    case 14: 
                    case 15: 
                    case 16: {
                        y = psize.height - tsize.height;
                    }
                }
            }
        }
        return new Point(x, y);
    }

    public String anchorName(int anchor) {
        switch (anchor) {
            case 10: {
                return "CENTER";
            }
            case 11: {
                return "NORTH";
            }
            case 12: {
                return "NORTHEAST";
            }
            case 13: {
                return "EAST";
            }
            case 14: {
                return "SOUTHEAST";
            }
            case 15: {
                return "SOUTH";
            }
            case 16: {
                return "SOUTHWEST";
            }
            case 17: {
                return "WEST";
            }
            case 18: {
                return "NORTHWEST";
            }
        }
        return "undefined";
    }
}


/*
 * Decompiled with CFR 0_114.
 */
package stanford.karel;

import acm.util.ErrorException;
import java.awt.Point;
import stanford.karel.KarelProgram;
import stanford.karel.KarelWorld;

public class Karel
implements Runnable {
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private static final int INFINITE = 99999999;
    private KarelWorld world = null;
    private int x = 1;
    private int y = 1;
    private int dir = 1;
    private int beepers;

    public void run() {
    }

    public void move() {
        this.checkWorld("move");
        if (this.world.checkWall(this.x, this.y, this.dir)) {
            throw new ErrorException("Karel is blocked");
        }
        this.setLocation(KarelWorld.adjacentPoint(this.x, this.y, this.dir));
        this.world.trace();
    }

    public void turnLeft() {
        this.checkWorld("turnLeft");
        this.setDirection(KarelWorld.leftFrom(this.dir));
        this.world.trace();
    }

    public void pickBeeper() {
        this.checkWorld("pickBeeper");
        int nb = this.world.getBeepersOnCorner(this.x, this.y);
        if (nb < 1) {
            throw new ErrorException("pickBeeper: No beepers on this corner");
        }
        this.world.setBeepersOnCorner(this.x, this.y, KarelWorld.adjustBeepers(nb, -1));
        this.setBeepersInBag(KarelWorld.adjustBeepers(this.getBeepersInBag(), 1));
        this.world.trace();
    }

    public void putBeeper() {
        this.checkWorld("putBeeper");
        int nb = this.getBeepersInBag();
        if (nb < 1) {
            throw new ErrorException("putBeeper: No beepers in bag");
        }
        this.world.setBeepersOnCorner(this.x, this.y, KarelWorld.adjustBeepers(this.world.getBeepersOnCorner(this.x, this.y), 1));
        this.setBeepersInBag(KarelWorld.adjustBeepers(nb, -1));
        this.world.trace();
    }

    public boolean frontIsClear() {
        this.checkWorld("frontIsClear");
        return !this.world.checkWall(this.x, this.y, this.dir);
    }

    public boolean frontIsBlocked() {
        this.checkWorld("frontIsBlocked");
        return this.world.checkWall(this.x, this.y, this.dir);
    }

    public boolean leftIsClear() {
        this.checkWorld("leftIsClear");
        return !this.world.checkWall(this.x, this.y, KarelWorld.leftFrom(this.dir));
    }

    public boolean leftIsBlocked() {
        this.checkWorld("leftIsBlocked");
        return this.world.checkWall(this.x, this.y, KarelWorld.leftFrom(this.dir));
    }

    public boolean rightIsClear() {
        this.checkWorld("rightIsClear");
        return !this.world.checkWall(this.x, this.y, KarelWorld.rightFrom(this.dir));
    }

    public boolean rightIsBlocked() {
        this.checkWorld("rightIsBlocked");
        return this.world.checkWall(this.x, this.y, KarelWorld.rightFrom(this.dir));
    }

    public boolean beepersPresent() {
        this.checkWorld("beepersPresent");
        if (this.world.getBeepersOnCorner(this.x, this.y) > 0) {
            return true;
        }
        return false;
    }

    public boolean noBeepersPresent() {
        this.checkWorld("noBeepersPresent");
        if (this.world.getBeepersOnCorner(this.x, this.y) == 0) {
            return true;
        }
        return false;
    }

    public boolean beepersInBag() {
        this.checkWorld("beepersInBag");
        if (this.getBeepersInBag() > 0) {
            return true;
        }
        return false;
    }

    public boolean noBeepersInBag() {
        this.checkWorld("noBeepersInBag");
        if (this.getBeepersInBag() == 0) {
            return true;
        }
        return false;
    }

    public boolean facingNorth() {
        this.checkWorld("facingNorth");
        if (this.dir == 0) {
            return true;
        }
        return false;
    }

    public boolean facingEast() {
        this.checkWorld("facingEast");
        if (this.dir == 1) {
            return true;
        }
        return false;
    }

    public boolean facingSouth() {
        this.checkWorld("facingSouth");
        if (this.dir == 2) {
            return true;
        }
        return false;
    }

    public boolean facingWest() {
        this.checkWorld("facingWest");
        if (this.dir == 3) {
            return true;
        }
        return false;
    }

    public boolean notFacingNorth() {
        this.checkWorld("notFacingNorth");
        if (this.dir != 0) {
            return true;
        }
        return false;
    }

    public boolean notFacingEast() {
        this.checkWorld("notFacingEast");
        if (this.dir != 1) {
            return true;
        }
        return false;
    }

    public boolean notFacingSouth() {
        this.checkWorld("notFacingSouth");
        if (this.dir != 2) {
            return true;
        }
        return false;
    }

    public boolean notFacingWest() {
        this.checkWorld("notFacingWest");
        if (this.dir != 3) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String[] newArgs = new String[args.length + 1];
        int i = 0;
        while (i < args.length) {
            newArgs[i] = args[i];
            ++i;
        }
        newArgs[args.length] = "program=stanford.karel.KarelProgram";
        KarelProgram.main(newArgs);
    }

    protected void start() {
        this.start(null);
    }

    protected void start(String[] args) {
        KarelProgram program = new KarelProgram();
        program.setStartupObject(this);
        program.start(args);
    }

    protected Point getLocation() {
        return new Point(this.x, this.y);
    }

    protected void setLocation(Point pt) {
        this.setLocation(pt.x, pt.y);
    }

    protected void setLocation(int x, int y) {
        if (this.world != null) {
            if (this.world.outOfBounds(x, y)) {
                throw new ErrorException("setLocation: Out of bounds");
            }
            Karel occupant = this.world.getKarelOnSquare(x, y);
            if (occupant == this) {
                return;
            }
            if (occupant != null) {
                throw new ErrorException("setLocation: Square is already occupied");
            }
        }
        int x0 = this.x;
        int y0 = this.y;
        this.x = x;
        this.y = y;
        if (this.world != null) {
            this.world.updateCorner(x, y);
            this.world.updateCorner(x0, y0);
        }
    }

    protected int getDirection() {
        return this.dir;
    }

    protected void setDirection(int dir) {
        this.dir = dir;
        if (this.world != null) {
            this.world.updateCorner(this.x, this.y);
        }
    }

    protected int getBeepersInBag() {
        return this.beepers;
    }

    protected void setBeepersInBag(int nBeepers) {
        this.beepers = nBeepers;
    }

    protected KarelWorld getWorld() {
        return this.world;
    }

    protected void setWorld(KarelWorld world) {
        this.world = world;
    }

    protected void checkWorld(String caller) {
        if (this.world == null) {
            throw new ErrorException(String.valueOf(caller) + ": Karel is not living in a world");
        }
    }
}


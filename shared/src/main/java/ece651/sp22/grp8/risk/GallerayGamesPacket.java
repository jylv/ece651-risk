package ece651.sp22.grp8.risk;

import java.io.Serializable;

public class GallerayGamesPacket implements Packet, Serializable {
    private GalleryGames galleryGames;

    public GallerayGamesPacket(GalleryGames galleryGames) {
        this.galleryGames = galleryGames;
    }

    @Override
    public void packPacket(Object galleryGames) {
        this.galleryGames = (GalleryGames)galleryGames;
    }

    @Override
    public GalleryGames getObject() {
        return this.galleryGames;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            GallerayGamesPacket arp = (GallerayGamesPacket) o;
            return arp.toString().equals(toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return galleryGames.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}

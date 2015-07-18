package com.lushen.otakureborn.gbaferandomizer.models;

import com.lushen.otakureborn.gbaferandomizer.MainActivity;

import java.util.Arrays;

/**
 * Created by lushe_000 on 7/16/2015.
 */
public class FECharacter {
    private Short nameIndex;            // offset 0, 2 bytes
    private Short bioIndex;             // offset 2, 2 bytes

    private Byte characterId;           // offset 4, 1 byte
    private Byte classId;               // offset 5, 1 byte

    private Byte portraitIndex;         // offset 6, 1 byte

    private Byte affinity;              // offset 9, 1 byte
    private Byte level;                 // offset 11, 1 byte

    private Byte baseHP;                // offset 12, 1 byte
    private Byte baseStr;               // offset 13, 1 byte
    private Byte baseSkl;               // offset 14, 1 byte
    private Byte baseSpd;               // offset 15, 1 byte
    private Byte baseDef;               // offset 16, 1 byte
    private Byte baseRes;               // offset 17, 1 byte
    private Byte baseLck;               // offset 18, 1 byte
    private Byte baseCon;               // offset 19, 1 byte

    private Byte swordLevel;            // offset 20, 1 byte
    private Byte spearLevel;            // offset 21, 1 byte
    private Byte axeLevel;              // offset 22, 1 byte
    private Byte bowLevel;              // offset 23, 1 byte
    private Byte staffLevel;            // offset 24, 1 byte
    private Byte animaLevel;            // offset 25, 1 byte
    private Byte lightLevel;            // offset 26, 1 byte
    private Byte darkLevel;             // offset 27, 1 byte

    private Byte hpGrowth;              // offset 28, 1 byte
    private Byte strGrowth;             // offset 29, 1 byte
    private Byte sklGrowth;             // offset 30, 1 byte
    private Byte spdGrowth;             // offset 31, 1 byte
    private Byte defGrowth;             // offset 32, 1 byte
    private Byte resGrowth;             // offset 33, 1 byte
    private Byte lckGrowth;             // offset 34, 1 byte

    private Byte paletteIndex;          // offset 35, 1 byte (unavailable in FE8)
    private Byte promotedPaletteIndex;  // offset 36, 1 byte (unavailable in FE8)

    private Byte customSprite;          // offset 37, 1 byte (only in FE7)
    private Byte promotedCustomSprite;  // offset 38, 1 byte (only in FE7)

    private Byte ability1;              // offset 40, 1 byte
    private Byte ability2;              // offset 41, 1 byte
    private Byte ability3;              // offset 42, 1 byte
    private Byte ability4;              // offset 43, 1 byte

    private Integer supportDataPointer; // offset 44, 4 bytes (address)

    private Boolean shouldResetCustomSprite;
    private byte[] rawData;

    public enum ClassAbility1 {
        NONE(0), MOUNTED_AID_SYSTEM(0x1), MOVE_AGAIN(0x2), STEAL(0x4), USE_LOCKPICKS(0x8),
        DANCE(0x10), PLAY(0x20), CRIT_BOOST(0x40), BALLISTA(0x80);

        private final int id;
        ClassAbility1(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum ClassAbility2 {
        NONE(0), PROMOTED(0x1), SUPPLY_DEPOT(0x2), SHOW_HORSEBACK_ICON(0x4), SHOW_DRAGON_ICON(0x8),
        SHOW_PEGASUS_ICON(0x10), LORD(0x20), FEMALE(0x40), BOSS(0x80);

        private final int id;
        ClassAbility2(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum ClassAbility3 {
        NONE(0), LORD_WEAPON_LOCK(0x1), WO_DAO_LOCK(0x2), DRAGONSTONE_LOCK(0x4), MORPHS_MAX_LEVEL_10(0x8),
        UNCONTROLLABLE(0x10), PEGASUS_TRIANGLE(0x20), ARMOR_TRIANGLE(0x40), STARTS_AS_NPC(0x80);

        private final int id;
        ClassAbility3(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum Affinity {
        NONE(0), FIRE(0x1), THUNDER(0x2), WIND(0x3), ICE(0x4), DARK(0x5), LIGHT(0x6), ANIMA(0x7);

        private final int id;
        Affinity(int id) { this.id = id; }
        public int getValue() { return id; }
        public static Affinity affinityWithValue(int id) {
            Affinity[] affinities = Affinity.values();
            for (int i = 0; i < affinities.length; i++) {
                if (affinities[i].getValue() == id) {
                    return affinities[i];
                }
            }

            return NONE;
        }
    }

    public FECharacter(byte[] rawCharacterData, MainActivity.GameType gameType) {
        rawData = rawCharacterData;

        nameIndex = (short)(rawCharacterData[0] | (rawCharacterData[1] << 8));
        bioIndex = (short)(rawCharacterData[2] | (rawCharacterData[3] << 8));

        characterId = rawCharacterData[4];
        classId = rawCharacterData[5];

        portraitIndex = rawCharacterData[6];

        affinity = rawCharacterData[9];

        level = rawCharacterData[11];

        baseHP = rawCharacterData[12];
        baseStr = rawCharacterData[13];
        baseSkl = rawCharacterData[14];
        baseSpd = rawCharacterData[15];
        baseDef = rawCharacterData[16];
        baseRes = rawCharacterData[17];
        baseLck = rawCharacterData[18];
        baseCon = rawCharacterData[19];

        swordLevel = rawCharacterData[20];
        spearLevel = rawCharacterData[21];
        axeLevel = rawCharacterData[22];
        bowLevel = rawCharacterData[23];
        staffLevel = rawCharacterData[24];
        animaLevel = rawCharacterData[25];
        lightLevel = rawCharacterData[26];
        darkLevel = rawCharacterData[27];

        hpGrowth = rawCharacterData[28];
        strGrowth = rawCharacterData[29];
        sklGrowth = rawCharacterData[30];
        spdGrowth = rawCharacterData[31];
        defGrowth = rawCharacterData[32];
        resGrowth = rawCharacterData[33];
        lckGrowth = rawCharacterData[34];

        if (gameType != MainActivity.GameType.FE8) {
            paletteIndex = rawCharacterData[35];
            promotedPaletteIndex = rawCharacterData[36];

            if (paletteIndex == 0) {
                paletteIndex = promotedPaletteIndex;
            }
        }

        if (gameType == MainActivity.GameType.FE7) {
            customSprite = rawCharacterData[37];
            promotedCustomSprite = rawCharacterData[38];
        }

        ability1 = rawCharacterData[40];
        ability2 = rawCharacterData[41];
        ability3 = rawCharacterData[42];
        ability4 = rawCharacterData[43];

        supportDataPointer = rawCharacterData[44] | (rawCharacterData[45] << 8) | (rawCharacterData[46] << 16) | (rawCharacterData[47] << 24);

        shouldResetCustomSprite = false;
    }

    public FECharacter(FECharacter otherCharacter) {
        super();

        rawData = Arrays.copyOf(otherCharacter.rawData, otherCharacter.rawData.length);

        affinity = otherCharacter.affinity;
        level = otherCharacter.level;

        baseHP = otherCharacter.baseHP;
        baseStr = otherCharacter.baseStr;
        baseSkl = otherCharacter.baseSkl;
        baseSpd = otherCharacter.baseSpd;
        baseDef = otherCharacter.baseDef;
        baseRes = otherCharacter.baseRes;
        baseLck = otherCharacter.baseLck;
        baseCon = otherCharacter.baseCon;

        hpGrowth = otherCharacter.hpGrowth;
        strGrowth = otherCharacter.strGrowth;
        sklGrowth = otherCharacter.sklGrowth;
        spdGrowth = otherCharacter.spdGrowth;
        defGrowth = otherCharacter.defGrowth;
        resGrowth = otherCharacter.resGrowth;
        lckGrowth = otherCharacter.lckGrowth;

        swordLevel = otherCharacter.swordLevel;
        spearLevel = otherCharacter.spearLevel;
        axeLevel = otherCharacter.axeLevel;
        bowLevel = otherCharacter.bowLevel;
        staffLevel = otherCharacter.staffLevel;
        animaLevel = otherCharacter.animaLevel;
        lightLevel = otherCharacter.lightLevel;
        darkLevel = otherCharacter.darkLevel;

        nameIndex = otherCharacter.nameIndex;
        bioIndex = otherCharacter.bioIndex;

        characterId = otherCharacter.characterId;
        classId = otherCharacter.classId;

        portraitIndex = otherCharacter.portraitIndex;

        paletteIndex = otherCharacter.paletteIndex;
        promotedPaletteIndex = otherCharacter.promotedPaletteIndex;

        customSprite = otherCharacter.customSprite;
        promotedCustomSprite = otherCharacter.promotedCustomSprite;

        ability1 = otherCharacter.ability1;
        ability2 = otherCharacter.ability2;
        ability3 = otherCharacter.ability3;
        ability4 = otherCharacter.ability4;

        supportDataPointer = otherCharacter.supportDataPointer;

        shouldResetCustomSprite = otherCharacter.shouldResetCustomSprite;
    }

    public byte[] getRawData() {
        return rawData;
    }

    public Integer totalGrowths() {
        return (hpGrowth << 8 >>> 8) + (strGrowth << 8 >>> 8) + (sklGrowth << 8 >>> 8) +
                (spdGrowth << 8 >>> 8) + (lckGrowth << 8 >>> 8) + (defGrowth << 8 >>> 8) +
                (resGrowth << 8 >>> 8);
    }

    public Integer totalBases() {
        return baseHP + baseStr + baseSkl + baseSpd + baseDef + baseRes + baseLck;
    }

    public void randomizeAffinity() {
        if (characterId == 0) {
            return;
        }
        Integer randomAffinity = MainActivity.rng.nextInt(7 + 1);
        affinity = (byte)Affinity.affinityWithValue(randomAffinity).ordinal();

        rawData[9] = affinity;
    }

    public void randomizeCON(Integer variance, Integer minimumCON, Integer classCON) {
        if (characterId == 0) {
            return;
        }

        if (variance > 0) {
            Integer adjustment = MainActivity.rng.nextInt(variance * 2 + 1) - variance;
            Integer newCON = Integer.valueOf(baseCon);

            newCON += adjustment;

            if (newCON + classCON < minimumCON) {
                newCON = minimumCON - classCON;
            }

            baseCon = newCON.byteValue();

            rawData[19] = baseCon;
        }
    }

    public void randomizeBases(Integer variance) {
        if (characterId == 0) {
            return;
        }

        Integer total = totalBases();

        if (variance > 0) {
            total += (MainActivity.rng.nextInt(variance * 2 + 1) - variance);
        }

        Integer newHP, newStr, newSkl, newSpd, newDef, newRes, newLck;

        newHP = newStr = newSkl = newSpd = newDef = newRes = newLck = 0;

        while (total > 0) {
            Integer statChoice = MainActivity.rng.nextInt(7);

            if (statChoice == 0) {
                // HP - Up to 5 per hit.
                Integer amount = 1 + MainActivity.rng.nextInt(Math.min(5, total));
                newHP += amount;
                total -= amount;
            }
            else if (statChoice < 4) {
                // STR, SPD, DEF - Up to 2 per hit.
                Integer amount = 1 + MainActivity.rng.nextInt(Math.min(2, total));
                if (statChoice == 1) newStr += amount;
                if (statChoice == 2) newSpd += amount;
                if (statChoice == 3) newDef += amount;
                total -= amount;
            }
            else if (statChoice < 6) {
                // SKL, RES - Up to 3 per hit.
                Integer amount = 1 + MainActivity.rng.nextInt(Math.min(3, total));
                if (statChoice == 4) newSkl += amount;
                if (statChoice == 5) newRes += amount;
                total -= amount;
            }
            else {
                // LCK - Up to 4 per hit.
                Integer amount = 1 + MainActivity.rng.nextInt(Math.min(4, total));
                newLck += amount;
                total -= amount;
            }
        }

        baseHP = newHP.byteValue();
        baseStr = newStr.byteValue();
        baseSkl = newSkl.byteValue();
        baseSpd = newSpd.byteValue();
        baseDef = newDef.byteValue();
        baseRes = newRes.byteValue();
        baseLck = newLck.byteValue();

        rawData[12] = baseHP;
        rawData[13] = baseStr;
        rawData[14] = baseSkl;
        rawData[15] = baseSpd;
        rawData[16] = baseDef;
        rawData[17] = baseRes;
        rawData[18] = baseLck;
    }

    public void randomizeGrowths(Integer variance, Boolean useMinimumGrowths) {
        if (characterId == 0) {
            return;
        }

        Integer total = totalGrowths();

        if (variance > 0) {
            total += (MainActivity.rng.nextInt(variance * 2 + 1) - variance);
        }

        Integer newHP, newStr, newSkl, newSpd, newDef, newRes, newLck;

        if (useMinimumGrowths) {
            newHP = newStr = newSkl = newSpd = newDef = newRes = newLck = 5;
            total -= 35;
        }
        else {
            newHP = newStr = newSkl =  newSpd = newDef = newRes = newLck = 0;
        }

        while (total > 0) {
            Integer statChoice = MainActivity.rng.nextInt(7);
            Integer amount = Math.min((MainActivity.rng.nextInt(10) + 1) * 5, total);

            if (newHP + amount > 255 &&
                    newStr + amount > 255 &&
                    newSkl + amount > 255 &&
                    newSpd + amount > 255 &&
                    newDef + amount > 255 &&
                    newRes + amount > 255 &&
                    newLck + amount > 255) {
                break;
            }

            if (statChoice == 0 && newHP + amount <= 255) {
                newHP += amount;
                total -= amount;
            }
            else if (statChoice == 1 && newStr + amount <= 255) {
                newStr += amount;
                total -= amount;
            }
            else if (statChoice == 2 && newSkl + amount <= 255) {
                newSkl += amount;
                total -= amount;
            }
            else if (statChoice == 3 && newSpd + amount <= 255) {
                newSpd += amount;
                total -= amount;
            }
            else if (statChoice == 4 && newDef + amount <= 255) {
                newDef += amount;
                total -= amount;
            }
            else if (statChoice == 5 && newRes + amount <= 255) {
                newRes += amount;
                total -= amount;
            }
            else if (statChoice == 6 && newLck + amount <= 255) {
                newLck += amount;
                total -= amount;
            }
        }

        hpGrowth = newHP.byteValue();
        strGrowth = newStr.byteValue();
        sklGrowth = newSkl.byteValue();
        spdGrowth = newSpd.byteValue();
        defGrowth = newDef.byteValue();
        resGrowth = newRes.byteValue();
        lckGrowth = newLck.byteValue();

        rawData[28] = hpGrowth;
        rawData[29] = strGrowth;
        rawData[30] = sklGrowth;
        rawData[31] = spdGrowth;
        rawData[32] = defGrowth;
        rawData[33] = resGrowth;
        rawData[34] = lckGrowth;
    }
}

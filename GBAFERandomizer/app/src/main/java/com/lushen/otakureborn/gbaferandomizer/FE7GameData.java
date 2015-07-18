package com.lushen.otakureborn.gbaferandomizer;

import android.media.effect.Effect;

/**
 * Created by lushe_000 on 7/16/2015.
 */
public class FE7GameData {

    public static final Integer DefaultCharacterTableOffset = 0xBDCE18;
    public static final Integer DefaultItemTableOffset = 0xBE222C;
    public static final Integer DefaultClassTableOffset = 0xBE015C;

    public static final Integer PointerToCharacterTableOffset = 0x17890;
    public static final Integer PointerToItemTableOffset = 0x16060;
    public static final Integer PointerToClassTableOffset = 0x178F0;

    public static final Integer CharacterCount = 254;
    public static final Integer ItemCount = 155;
    public static final Integer ClassCount = 99;

    public static final Integer CharacterEntrySize = 52;
    public static final Integer ItemEntrySize = 36;
    public static final Integer ClassEntrySize = 84;

    public static final Integer ChapterUnitEntrySize = 16;

    public enum EffectivenessPointers {
        NONE(0), KNIGHTS_AND_CAVALRY(0x8C97E9C), KNIGHTS(0x8C97E96), DRAGONS(0x8C97EC5), CAVALRY(0x8C97EB7), MYRMIDON(0x8C97EAD), FLIERS(0x8C97ED2), DRAGONS2(0x8C97EC5);

        private final int id;
        EffectivenessPointers(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum WeaponRank {
        NONE(0), E(0x1), D(0x1F), C(0x47), B(0x79), A(0xB5), S(0xFB);

        private final int id;
        WeaponRank(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum StatBonusPointers {
        NONE(0), UBER_SPEAR(0x8C99010), DURANDAL(0x8C9901C), ARMADS(0x8C99028), AUREOLA(0x8C99034), SOL_KATTI(0x8C99040), DRAGONSTONE(0x8C9904C), FORBLAZE(0x8C98F98);

        private final int id;
        StatBonusPointers(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum ClassList {
        NONE(0),

        ELIWOOD_LORD(0x1), LYN_LORD(0x2), HECTOR_LORD(0x3),
        LORD_KNIGHT(0x7), BLADE_LORD(0x8), GREAT_LORD(0x9),

        MERCENARY(0xA), MERCENARY_F_UNUSED(0xB), HERO(0xC), HERO_F_UNUSED(0xD),
        MYRMIDON(0xE), MYRMIDON_F_UNUSED(0xF), SWORDMASTER(0x10), SWORDMASTER_F(0x11),
        FIGHTER(0x12), WARRIOR(0x13),
        KNIGHT(0x14), KNIGHT_F_UNUSED(0x15), GENERAL(0x16), GENERAL_F_UNUSED(0x17),
        ARCHER(0x18), ARCHER_F(0x19), SNIPER(0x1A), SNIPER_F(0x1B),
        MONK(0x1C), CLERIC(0x1D), BISHOP(0x1E), BISHOP_F(0x1F),
        MAGE(0x20), MAGE_F(0x21), SAGE(0x22), SAGE_F(0x23),
        SHAMAN(0x24), SHAMAN_F_UNUSED(0x25), DRUID(0x26), DRUID_F_UNUSED(0x27),
        CAVALIER(0x28), CAVALIER_F_UNUSED(0x29), PALADIN(0x2A), PALADIN_F(0x2B),
        TROUBADOUR(0x2C), VALKYRIE(0x2D),
        NOMAD(0x2E), NOMAD_F_UNUSED(0x2F), NOMAD_TROOPER(0x30), NOMAD_TROOPER_F_UNUSED(0x31),
        PEGASUS_KNIGHT(0x32), FALCONKNIGHT(0x33),
        WYVERN_KNIGHT(0x34), WYVERN_KNIGHT_F_UNUSED(0x35), WYVERN_LORD(0x36), WYVERN_LORD_F(0x37),

        SOLDIER(0x38),
        BRIGAND(0x39),
        PIRATE(0x3A), BERSERKER(0x3B),
        THIEF(0x3C), THIEF_F_UNUSED(0x3D), ASSASSIN(0x3E),

        DANCER(0x40), BARD(0x41),

        ARCHSAGE(0x42);

        private final int id;
        ClassList(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum CharacterList {
        NONE(0),

        ELIWOOD(0x1), HECTOR(0x2), TUTORIAL_LYN(0x3), RAVEN(0x4), GEITZ(0x5), GUY(0x6),
        KAREL(0x7), DORCAS(0x8), BARTRE(0x9), OSWIN(0xB), FARGUS(0xC), TUTORIAL_WIL(0xD),
        REBECCA(0xE), LOUISE(0xF), LUCIUS(0x10), SERRA(0x11), RENAULT(0x12), ERK(0x13),
        NINO(0x14), PENT(0x15), CANAS(0x16), TUTORIAL_KENT(0x17), TUTORIAL_SAIN(0x18),
        LOWEN(0x19), MARCUS(0x1A), PRISCILLA(0x1B), TUTORIAL_RATH(0x1C), TUTORIAL_FLORINA(0x1D),
        FIORA(0x1E), FARINA(0x1F), HEATH(0x20), VAIDA(0x21), HAWKEYE(0x22), MATTHEW(0x23),
        JAFFAR(0x24), NINIAN(0x25), NILS(0x26), ATHOS(0x27), MERLINUS(0x28), NILS_FINAL(0x29),
        UTHER(0x2A), VAIDA_BOSS(0x2B), WALLACE(0x2C), LYN(0x2D), WIL(0x2E), KENT(0x2F), SAIN(0x30),
        FLORINA(0x31), RATH(0x32), DART(0x33), ISADORA(0x34), ELENORA(0x35), LEGAULT(0x36),
        KARLA(0x37), HARKEN(0x38),

        LEILA(0x39), BRAMIMOND(0x3A), KISHUNA(0x3B),

        GROZNYI(0x3C),WIRE(0x3D), ZAGAN(0x3F), BOIES(0x40), PUZON(0x41), SANTALS(0x43),
        NERGAL(0x44), ERIK(0x45), SEALEN(0x46), BAUKER(0x47), BERNARD(0x48), DAMIAN(0x49),
        ZOLDAM(0x4A), UHAI(0x4B), AION(0x4C), DARIN(0x4D), CAMERON(0x4E), OLEG(0x4F),
        EUBANS(0x50), URSULA(0x51), PAUL(0x53), JASMINE(0x54), MORPH_JERME(0x56),
        PASCAL(0x57), KENNETH(0x58), JERME(0x59), MAXIME(0x5A), SONIA(0x5B), TEODOR(0x5C),
        GEORG(0x5D), KAIM(0x5E), DENNING(0x60),

        LLOYD_A(0x63), LLOYD_B(0x64), LINUS_A(0x65), LINUS_B(0x66),

        ZEPHIEL(0x7A), ELBERT(0x7B), BRENDAN(0x84),

        LIMSTELLA(0x85), DRAGON(0x86),

        BATTA(0x87), ZUGU(0x89), GLASS(0x8D), MIGAL(0x8E), CARJIGA(0x94), BUG(0x99),
        NATALIE(0x9E), BOOL(0x9F), HEINTZ(0xA6), BEYARD(0xAD), YOGI(0xB6), EAGLER(0xBE),
        LUNDGREN(0xC5),

        MORPH_LLOYD(0xF4), MORPH_LINUS(0xF5), MORPH_BRENDAN(0xF6), MORPH_UHAI(0xF7),
        MORPH_URSULA(0xF8), MORPH_KENNETH(0xF9), MORPH_DARIN(0xFA);

        private final int id;
        CharacterList(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public enum ItemList {
        NONE(0),

        IRON_SWORD(0x1), SLIM_SWORD(0x2), STEEL_SWORD(0x3), SILVER_SWORD(0x4), IRON_BLADE(0x5),
        STEEL_BLADE(0x6), SILVER_BLADE(0x7), POISON_SWORD(0x8), RAPIER(0x9), MANI_KATTI(0xA),
        BRAVE_SWORD(0xB), WO_DAO(0xC), KILLING_EDGE(0xD), ARMORSLAYER(0xE), WYRMSLAYER(0xF),
        LIGHT_BRAND(0x10), RUNESWORD(0x11), LANCEREAVER(0x12), LONGSWORD(0x13),

        IRON_LANCE(0x14), SLIM_LANCE(0x15), STEEL_LANCE(0x16), SILVER_LANCE(0x17),
        POISON_LANCE(0x18), BRAVE_LANCE(0x19), KILLER_LANCE(0x1A), HORSESLAYER(0x1B),
        JAVELIN(0x1C), SPEAR(0x1D), AXEREAVER(0x1E),

        IRON_AXE(0x1F), STEEL_AXE(0x20), SILVER_AXE(0x21), POISON_AXE(0x22), BRAVE_AXE(0x23),
        KILLER_AXE(0x24), HALBERD(0x25), HAMMER(0x26), DEVIL_AXE(0x27), HAND_AXE(0x28),
        TOMAHAWK(0x29), SWORDREAVER(0x2A), SWORDSLAYER(0x2B),

        IRON_BOW(0x2C), STEEL_BOW(0x2D), SILVER_BOW(0x2E), POISON_BOW(0x2F), KILLER_BOW(0x30),
        BRAVE_BOW(0x31), SHORT_BOW(0x32), LONGBOW(0x33), BALLISTA(0x34), IRON_BALLISTA(0x35),
        KILLER_BALLISTA(0x36),

        FIRE(0x37), THUNDER(0x38), ELFIRE(0x39), BOLTING(0x3A), FIMBULVETR(0x3B), FORBLAZE(0x3C),
        EXCALIBUR(0x3D),

        LIGHTNING(0x3E), SHINE(0x3F), DIVINE(0x40), PURGE(0x41), AURA(0x42), LUCE(0x43),

        FLUX(0x44), LUNA(0x45), NOSFERATU(0x46), ECLIPSE(0x47), FENRIR(0x48), GESPENST(0x49),

        HEAL(0x4A), MEND(0x4B), RECOVER(0x4C), PHYSIC(0x4D), FORTIFY(0x4E), RESTORE(0x4F),
        SILENCE(0x50), SLEEP(0x51), BERSERK(0x52), WARP(0x53), RESCUE(0x54), TORCH_STAFF(0x55),
        HAMMERNE(0x56), UNLOCK(0x57), BARRIER(0x58),

        DRAGON_AXE(0x59),

        ANGELIC_ROBE(0x5A), ENERGY_RING(0x5B), SECRET_BOOK(0x5C), SPEEDWINGS(0x5D),
        GODDESS_ICON(0x5E), DRAGONSHIELD(0x5F), TALISMAN(0x60), BOOTS(0x61), BODY_RING(0x62),

        HERO_CREST(0x63), KNIGHT_CREST(0x64), ORIONS_BOLT(0x65), ELYSIAN_WHIP(0x66),
        GUIDING_RING(0x67),

        CHEST_KEYS(0x68), DOOR_KEYS(0x69), LOCKPICKS(0x6A),

        VULNERARY(0x6B), ELIXIR(0x6C), PURE_WATER(0x6D), ANTITOXIN(0x6E), TORCH(0x6F),
        DELPHI_SHIELD(0x70), MEMBER_CARD(0x71), SILVER_CARD(0x72),

        WHITE_GEM(0x73), BLUE_GEM(0x74), RED_GEM(0x75),

        UNUSED_GOLD(0x76), UBER_SPEAR(0x77), CHEST_KEYS_5(0x78), MINE(0x79), LIGHT_RUNE(0x7A),
        IRON_RUNE(0x7B),

        FILLAS_MIGHT(0x7C), NINIS_GRACE(0x7D), THORS_IRE(0x7E), SETS_LITANY(0x7F),

        EMBLEM_SWORD(0x80), EMBLEM_SPEAR(0x81), EMBLEM_AXE(0x82), EMBLEM_BOW(0x83),

        DURANDAL(0x84), ARMADS(0x85), AUREOLA(0x86),

        EARTH_SEAL(0x87), AFA_DROPS(0x88), HEAVEN_SEAL(0x89), EMBLEM_SEAL(0x8A), FELL_CONTRACT(0x8B),

        SOL_KATTI(0x8C), WOLF_BEIL(0x8D),

        ERESHKIGAL(0x8E), FLAMETONGUE(0x8F), REGAL_BLADE(0x90), REX_HASTA(0x91), BASILIKOS(0x92),
        RIENFLECHE(0x93),

        HEAVY_SPEAR(0x94), SHORT_SPEAR(0x95),

        OCEAN_SEAL(0x96),

        UNUSED_3000G(0x97), UNUSED_5000G(0x98),

        WIND_SWORD(0x99),

        UNUSED_SUPER_VULNERARY(0x9A);

        private final int id;
        ItemList(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    public static EffectivenessPointers randomEffectiveness() {
        Integer randomNumber = MainActivity.rng.nextInt(6);

        switch(randomNumber) {
            case 0: return EffectivenessPointers.KNIGHTS_AND_CAVALRY;
            case 1: return EffectivenessPointers.KNIGHTS;
            case 2: return EffectivenessPointers.DRAGONS;
            case 3: return EffectivenessPointers.CAVALRY;
            case 4: return EffectivenessPointers.MYRMIDON;
            case 5: return EffectivenessPointers.FLIERS;
            default: return EffectivenessPointers.NONE;
        }
    }

    public static StatBonusPointers randomStatBonus() {
        Integer randomNumber = MainActivity.rng.nextInt(5);

        switch(randomNumber) {
            case 0: return StatBonusPointers.DURANDAL;
            case 1: return StatBonusPointers.ARMADS;
            case 2: return StatBonusPointers.SOL_KATTI;
            case 3: return StatBonusPointers.AUREOLA;
            case 4: return StatBonusPointers.FORBLAZE;
            default: return StatBonusPointers.NONE;
        }
    }
}

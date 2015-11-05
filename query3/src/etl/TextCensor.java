package etl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class TextCensor.
 */
public class TextCensor {

  /**
   * Inits the.
   */
  public void generateDictionary() {
    String line = null;
    // try (BufferedReader reader = new BufferedReader(new
    // FileReader(BANNED_FILE))) {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(BANNED_FILE), Charset.forName("UTF-8")))) {

      HashSet<String> tempDictionary = new HashSet<String>();
      while ((line = reader.readLine()) != null) {
        tempDictionary.add(rot13(line));
      }

      for (String s : tempDictionary) {
        System.out.println("dictionary.add(\"" + s + "\");");
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public String rot13(String input) {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if ((c >= 'a' && c <= 'm') || (c >= 'A' && c <= 'M')) {
        c += 13;
      } else if ((c >= 'n' && c <= 'z') || (c >= 'N' && c <= 'Z')) {
        c -= 13;
      }
      buffer.append(c);
    }
    return buffer.toString();
  }

  /**
   * Censor.
   *
   * @param words
   *          the words
   */
  public String censor(String text) {
    StringBuffer buffer = new StringBuffer();
    Pattern p = Pattern.compile("([\\p{Alnum}]+)");
    Matcher m = p.matcher(text);
    while (m.find()) {
      String word = m.group();
      String lowerCase = word.toLowerCase();
      if (dictionary.contains(lowerCase)) {
        m.appendReplacement(buffer, sanitize(word));
      }
    }
    m.appendTail(buffer);
    return buffer.toString();
  }

  /**
   * Sanitize.
   *
   * @param word
   *          the word
   * @return the string
   */
  public String sanitize(String word) {
    String sanitized = word.substring(0, 1) + word.substring(1, word.length() - 1).replaceAll("[^\0]", "*")
        + word.substring(word.length() - 1, word.length());
    return sanitized;
  }

  /** The banned file. */
  private final String BANNED_FILE = "banned.txt";

  /** The dictionary. */
  private static final HashSet<String> dictionary;

  static {
    dictionary = new HashSet<String>();
    dictionary.add("ballsack");
    dictionary.add("titwank");
    dictionary.add("cocksucking");
    dictionary.add("knobend");
    dictionary.add("boiolas");
    dictionary.add("fux0r");
    dictionary.add("pusse");
    dictionary.add("pussi");
    dictionary.add("jackoff");
    dictionary.add("buttmuch");
    dictionary.add("fuck");
    dictionary.add("fingerfuck");
    dictionary.add("pussy");
    dictionary.add("15619cctest");
    dictionary.add("coksucka");
    dictionary.add("hardcoresex");
    dictionary.add("smut");
    dictionary.add("carpetmuncher");
    dictionary.add("m45terbate");
    dictionary.add("kumming");
    dictionary.add("fuckingshitmotherfucker");
    dictionary.add("c0ck");
    dictionary.add("blowjobs");
    dictionary.add("goatse");
    dictionary.add("blowjob");
    dictionary.add("assram");
    dictionary.add("queer");
    dictionary.add("heshe");
    dictionary.add("fuckhead");
    dictionary.add("horny");
    dictionary.add("piss");
    dictionary.add("flange");
    dictionary.add("knobed");
    dictionary.add("4r5e");
    dictionary.add("knobjokey");
    dictionary.add("d1ck");
    dictionary.add("shitters");
    dictionary.add("sh!t");
    dictionary.add("shits");
    dictionary.add("shitty");
    dictionary.add("dyke");
    dictionary.add("b17ch");
    dictionary.add("kawk");
    dictionary.add("whoar");
    dictionary.add("fistfuck");
    dictionary.add("twathead");
    dictionary.add("vulva");
    dictionary.add("masterbations");
    dictionary.add("shite");
    dictionary.add("anal");
    dictionary.add("jizm");
    dictionary.add("dildo");
    dictionary.add("assfucker");
    dictionary.add("cunilingus");
    dictionary.add("jizz");
    dictionary.add("cock");
    dictionary.add("jap");
    dictionary.add("cummer");
    dictionary.add("booobs");
    dictionary.add("v1gra");
    dictionary.add("slut");
    dictionary.add("fag");
    dictionary.add("motherfucking");
    dictionary.add("masterbat3");
    dictionary.add("fistfuckings");
    dictionary.add("bastard");
    dictionary.add("fistfucker");
    dictionary.add("fanyy");
    dictionary.add("cumshot");
    dictionary.add("masterbate");
    dictionary.add("sex");
    dictionary.add("scrote");
    dictionary.add("l3itch");
    dictionary.add("fistfucked");
    dictionary.add("muff");
    dictionary.add("balls");
    dictionary.add("fcuking");
    dictionary.add("pigfucker");
    dictionary.add("lmao");
    dictionary.add("crap");
    dictionary.add("phukked");
    dictionary.add("cyalis");
    dictionary.add("cocks");
    dictionary.add("tosser");
    dictionary.add("cuntlicking");
    dictionary.add("donkeyribber");
    dictionary.add("kondum");
    dictionary.add("cyberfucking");
    dictionary.add("5h1t");
    dictionary.add("hore");
    dictionary.add("muthafuckker");
    dictionary.add("cum");
    dictionary.add("rectum");
    dictionary.add("motherfuckings");
    dictionary.add("mothafuckers");
    dictionary.add("dink");
    dictionary.add("ejaculating");
    dictionary.add("mothafuckaz");
    dictionary.add("cnut");
    dictionary.add("cuntlicker");
    dictionary.add("boner");
    dictionary.add("penis");
    dictionary.add("p0rn");
    dictionary.add("v14gra");
    dictionary.add("fingerfuckers");
    dictionary.add("kum");
    dictionary.add("mothafuckas");
    dictionary.add("knob");
    dictionary.add("b!tch");
    dictionary.add("labia");
    dictionary.add("fistfucking");
    dictionary.add("pissoff");
    dictionary.add("wtff");
    dictionary.add("bunnyfucker");
    dictionary.add("twunt");
    dictionary.add("boobs");
    dictionary.add("mutherfucker");
    dictionary.add("motherfuckers");
    dictionary.add("cocksucked");
    dictionary.add("cyberfuck");
    dictionary.add("clitoris");
    dictionary.add("knobhead");
    dictionary.add("skank");
    dictionary.add("whoreshit");
    dictionary.add("cocksucker");
    dictionary.add("gaysex");
    dictionary.add("goddamn");
    dictionary.add("twunter");
    dictionary.add("wanky");
    dictionary.add("butt");
    dictionary.add("niggas");
    dictionary.add("penisfucker");
    dictionary.add("hotsex");
    dictionary.add("b1tch");
    dictionary.add("hell");
    dictionary.add("fucks");
    dictionary.add("bestial");
    dictionary.add("niggah");
    dictionary.add("mothafuck");
    dictionary.add("ejaculation");
    dictionary.add("beastiality");
    dictionary.add("homo");
    dictionary.add("hoare");
    dictionary.add("niggaz");
    dictionary.add("bitch");
    dictionary.add("fucka");
    dictionary.add("pawn");
    dictionary.add("gangbanged");
    dictionary.add("mothafucked");
    dictionary.add("assho");
    dictionary.add("jiz");
    dictionary.add("fellate");
    dictionary.add("cokmuncher");
    dictionary.add("mothafucker");
    dictionary.add("fucking");
    dictionary.add("mothafucka");
    dictionary.add("fecker");
    dictionary.add("phonesex");
    dictionary.add("shiting");
    dictionary.add("bollock");
    dictionary.add("viagra");
    dictionary.add("b00bs");
    dictionary.add("tittywank");
    dictionary.add("fuker");
    dictionary.add("phuked");
    dictionary.add("buceta");
    dictionary.add("mothafucks");
    dictionary.add("masterbation");
    dictionary.add("gangbang");
    dictionary.add("fudgepacker");
    dictionary.add("rimming");
    dictionary.add("gangbangs");
    dictionary.add("bollok");
    dictionary.add("ejaculate");
    dictionary.add("c0cksucker");
    dictionary.add("cunillingus");
    dictionary.add("kums");
    dictionary.add("dinks");
    dictionary.add("semen");
    dictionary.add("nigger");
    dictionary.add("ejakulate");
    dictionary.add("horniest");
    dictionary.add("pissing");
    dictionary.add("pissflaps");
    dictionary.add("pube");
    dictionary.add("shitfull");
    dictionary.add("muthafecker");
    dictionary.add("motherfuckka");
    dictionary.add("hoer");
    dictionary.add("duche");
    dictionary.add("nazi");
    dictionary.add("cockface");
    dictionary.add("ma5terb8");
    dictionary.add("pimpis");
    dictionary.add("pissin");
    dictionary.add("twatty");
    dictionary.add("coon");
    dictionary.add("rimjaw");
    dictionary.add("feck");
    dictionary.add("turd");
    dictionary.add("dickhead");
    dictionary.add("cawk");
    dictionary.add("m0fo");
    dictionary.add("arse");
    dictionary.add("prick");
    dictionary.add("doggin");
    dictionary.add("breasts");
    dictionary.add("phuking");
    dictionary.add("fuckwit");
    dictionary.add("fagot");
    dictionary.add("fellatio");
    dictionary.add("l3ich");
    dictionary.add("fuckin");
    dictionary.add("sonofabitch");
    dictionary.add("fannyfucker");
    dictionary.add("whore");
    dictionary.add("numbnuts");
    dictionary.add("cuntlick");
    dictionary.add("fux");
    dictionary.add("n1gger");
    dictionary.add("lusting");
    dictionary.add("fuckwhit");
    dictionary.add("pisser");
    dictionary.add("pisses");
    dictionary.add("ejaculates");
    dictionary.add("ejaculated");
    dictionary.add("cocksuka");
    dictionary.add("shitings");
    dictionary.add("beastial");
    dictionary.add("nobhead");
    dictionary.add("phuck");
    dictionary.add("faggitt");
    dictionary.add("cok");
    dictionary.add("kock");
    dictionary.add("nigga");
    dictionary.add("mothafuckings");
    dictionary.add("m0f0");
    dictionary.add("w00se");
    dictionary.add("spunk");
    dictionary.add("nobjokey");
    dictionary.add("sh1t");
    dictionary.add("shited");
    dictionary.add("cox");
    dictionary.add("cums");
    dictionary.add("fingerfucked");
    dictionary.add("fuckings");
    dictionary.add("hoar");
    dictionary.add("dirsa");
    dictionary.add("vagina");
    dictionary.add("buttplug");
    dictionary.add("asswhole");
    dictionary.add("cyberfuc");
    dictionary.add("cunts");
    dictionary.add("pricks");
    dictionary.add("fuk");
    dictionary.add("dogfucker");
    dictionary.add("chink");
    dictionary.add("cipa");
    dictionary.add("cyberfucked");
    dictionary.add("cunt");
    dictionary.add("shitey");
    dictionary.add("titties");
    dictionary.add("motherfucks");
    dictionary.add("fagging");
    dictionary.add("cl1t");
    dictionary.add("f4nny");
    dictionary.add("cyberfucker");
    dictionary.add("mutha");
    dictionary.add("fucked");
    dictionary.add("anus");
    dictionary.add("masterb8");
    dictionary.add("pussys");
    dictionary.add("cockmunch");
    dictionary.add("cumming");
    dictionary.add("kunilingus");
    dictionary.add("dogging");
    dictionary.add("lust");
    dictionary.add("tw4t");
    dictionary.add("motherfuckin");
    dictionary.add("motherfuck");
    dictionary.add("scroat");
    dictionary.add("fingerfucking");
    dictionary.add("felching");
    dictionary.add("shithead");
    dictionary.add("fistfucks");
    dictionary.add("clit");
    dictionary.add("fukwhit");
    dictionary.add("n1gga");
    dictionary.add("phuk");
    dictionary.add("masterbat");
    dictionary.add("snatch");
    dictionary.add("fucker");
    dictionary.add("fags");
    dictionary.add("nutsack");
    dictionary.add("fuckers");
    dictionary.add("phuq");
    dictionary.add("booooooobs");
    dictionary.add("fcuker");
    dictionary.add("shitfuck");
    dictionary.add("jism");
    dictionary.add("mofo");
    dictionary.add("fcuk");
    dictionary.add("shitting");
    dictionary.add("fukwit");
    dictionary.add("shitter");
    dictionary.add("tit");
    dictionary.add("jerkoff");
    dictionary.add("fooker");
    dictionary.add("phukking");
    dictionary.add("doosh");
    dictionary.add("a55");
    dictionary.add("pissers");
    dictionary.add("fuks");
    dictionary.add("faggs");
    dictionary.add("damn");
    dictionary.add("nigg3r");
    dictionary.add("muther");
    dictionary.add("ma5terbate");
    dictionary.add("teets");
    dictionary.add("titfuck");
    dictionary.add("knobjocky");
    dictionary.add("lmfao");
    dictionary.add("whoreanal");
    dictionary.add("shitted");
    dictionary.add("biatch");
    dictionary.add("shit");
    dictionary.add("mof0");
    dictionary.add("masturbate");
    dictionary.add("schlong");
    dictionary.add("cockhead");
    dictionary.add("tittiefucker");
    dictionary.add("wank");
    dictionary.add("motherfucked");
    dictionary.add("wang");
    dictionary.add("shitdick");
    dictionary.add("tittyfuck");
    dictionary.add("fistfuckers");
    dictionary.add("whore4r5e");
    dictionary.add("5hit");
    dictionary.add("nigg4h");
    dictionary.add("jerk");
    dictionary.add("booooobs");
    dictionary.add("cocksuck");
    dictionary.add("pron");
    dictionary.add("dlck");
    dictionary.add("fukkin");
    dictionary.add("shittings");
    dictionary.add("motherfucker");
    dictionary.add("bum");
    dictionary.add("fagots");
    dictionary.add("pussies");
    dictionary.add("bloody");
    dictionary.add("clits");
    dictionary.add("bugger");
    dictionary.add("fingerfucks");
    dictionary.add("fingerfucker");
    dictionary.add("cyberfuckers");
    dictionary.add("phuks");
    dictionary.add("cocksucks");
    dictionary.add("bestiality");
    dictionary.add("knobead");
    dictionary.add("fook");
    dictionary.add("mothafucking");
    dictionary.add("boooobs");
    dictionary.add("masochist");
    dictionary.add("nobjocky");
    dictionary.add("kummer");
    dictionary.add("cockmuncher");
    dictionary.add("bellend");
    dictionary.add("smegma");
    dictionary.add("faggot");
    dictionary.add("cunnilingus");
    dictionary.add("mothafuckin");
    dictionary.add("kondums");
    dictionary.add("niggers");
    dictionary.add("ass");
    dictionary.add("fuckme");
    dictionary.add("dick");
    dictionary.add("ejaculatings");
    dictionary.add("fannyflaps");
    dictionary.add("assfukka");
    dictionary.add("dildos");
    dictionary.add("omg");
    dictionary.add("poop");
    dictionary.add("fukker");
    dictionary.add("boob");
    dictionary.add("wanker");
    dictionary.add("scrotum");
    dictionary.add("fuckheads");
    dictionary.add("cocksukka");
    dictionary.add("twat");

  }
}

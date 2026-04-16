package model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Bolovanje extends Prekid_Rada {
    private String filePath;

	public Bolovanje(Zaposleni z, LocalDate datum_od, LocalDate datum_do, PrekidRadaState stanje,
			String filePath) {
		super(z, datum_od, datum_do, stanje);
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	

}
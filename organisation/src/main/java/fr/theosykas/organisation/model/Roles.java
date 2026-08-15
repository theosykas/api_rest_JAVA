package fr.theosykas.organisation.model;

public enum Roles {
	ADMIN(4),
	READER(1),
	WRITER(2),
	MODERATOR(3);

	private final int lvl;

	Roles(int lvl) {
		this.lvl = lvl;
	}

	public boolean atLeast(Roles required) {
		return this.lvl >= required.lvl;
	}
}

// ici on a un sys de lvl qui permet de dire que si je suis a partir du lvl 3
// admin a forcememnt le droit 

// un containns lui va juste check l'actuelle ici on pondere 

// seuil = MODERATOR (3)
//                       │
//    READER  WRITER  MODERATOR  ADMIN
//      (1)     (2)      (3)      (4)
//       │       │        │        │
//       ✗       ✗        ▓▓▓▓▓▓▓▓▓▓▓   ← autorises
//                        └────────┘
//                     tout ce qui est >= 3
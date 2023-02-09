package Model.Enum;

/**
 * The names of the planets to be fetched from the API.
 * The ID:s of the celestial bodies in the API is in French and that's why they are in French here.
 * They are in the order of closest to the planet the orbit around to the furthest and in the order that the planets are found in the solar system from closest to furthest from the sun.
 * (Starts with the moons of Mercury then Venus then Earth etc. etc.)
 */

public enum Moons {
    //Venus has moons
    //Mercury has no moons
    //Earths moon:
    lune,
    //Mars moons:
    phobos,
    deimos,
    //Jupiters moons:
    metis,
    adrestee,
    amalthee,
    thebe,
    io,
    europe,
    ganymede,
    callisto,
    //Saturns moons (Has 53 named moons, dont know which ones to choose )

    //Uranus moons
    miranda,
    ariel,
    umbriel,
    titania,
    oberon,
    //Neptunes moons
    naiade,
    thalassa,
    despina,
    galatee,
    larissa,
    protee,
    triton,
    neriede
}

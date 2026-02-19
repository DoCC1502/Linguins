<template>
  <section class="page">
    <div class="card impressum-card">
      <h1>Impressum</h1>

      <h2>Angaben gemäß § 5 TMG</h2>
      <p>
        Linguins Learning Platform<br />
        Wexstraße 19–23<br />
        1200 Wien<br />
        Österreich
      </p>

      <!-- GOOGLE MAPS -->
      <h3>Standort</h3>
      <div class="map-wrapper">
        <iframe
            src="https://www.google.com/maps?q=Wexstraße%2019-23%2C%201200%20Wien&output=embed"
            loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"
        ></iframe>
      </div>

      <h3>Vertreten durch</h3>
      <p>
        Ramis Ekici<br />
        Geschäftsführer
      </p>

      <h3>Kontakt</h3>
      <p>
        Telefon: +43 1 587 64 23<br />
        E-Mail:
        <a href="mailto:rekici@student.tgm.ac.at">
          rekici@student.tgm.ac.at
        </a>
      </p>

      <h3>Registereintrag</h3>
      <p>
        Handelsregister: Amtsgericht Wien<br />
        Umsatzsteuer-ID gemäß §27 a Umsatzsteuergesetz: AT123456789
      </p>

      <h3>
        Verantwortlich für den Inhalt nach § 55 Abs. 2 RStV
      </h3>
      <p>
        Elyesa Imamoglu<br />
        Wexstraße 19–23<br />
        1200 Wien<br />
        Österreich
      </p>

      <h3>Haftungshinweis</h3>
      <p class="muted">
        Trotz sorgfältiger inhaltlicher Kontrolle übernehmen wir keine Haftung
        für die Inhalte externer Links. Für den Inhalt der verlinkten Seiten sind
        ausschließlich deren Betreiber verantwortlich.
      </p>
    </div>
  </section>
</template>

<style scoped>
.impressum-card {
  max-width: 900px;
}

/* TYPO */
h2 {
  margin-top: 2rem;
  font-size: 1.2rem;
}

h3 {
  margin-top: 1.8rem;
  font-size: 1rem;
}

p {
  line-height: 1.6;
}

a {
  color: var(--primary);
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

.muted {
  color: var(--text-muted);
}

/* GOOGLE MAPS */
.map-wrapper {
  margin-top: 1rem;
  margin-bottom: 2rem;
  border-radius: 14px;
  overflow: hidden;
  border: 1px solid var(--border-soft);
}

.map-wrapper iframe {
  width: 100%;
  height: 320px;
  border: none;
  filter: grayscale(0.85) contrast(1.1);
}
</style>

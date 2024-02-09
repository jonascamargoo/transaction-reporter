/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}"
  ],
  theme: {
    extend: {},
    fontFamily: {
      // inserir a fonte posteriormente
    }
  },
  plugins: [],
}

// se adicionar cores customizadas, as cores padrão do tailwind serao removidas! Usar bg-[#******]
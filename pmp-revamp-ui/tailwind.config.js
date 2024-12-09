/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
  content: [
    "./src/**/*",
  ],
  theme: {
    extend: {
      width: {
        'dynamic': 'var(--dropdown-width)',
      },
      backgroundColor: theme => ({
        ...theme('colors'),
        'anti-flash-white': '#F1F3F6',
        'tory-blue': '#1447b2',
        'fruit-salad': '#589D4C',
        'moderate-red': '#B43F3F',
        'snow-white': '#FCFCFC',
        'medium-gray': '#707070',
        'platinum-gray': '#EBEBEB',
        'floral-white': '#FFF9F0',
        'alice-green': '#F2F5FC',
      }),
      textColor: theme => ({
        ...theme('colors'),
        'tory-blue': '#1447b2',
        'charcoal-gray': '#454545',
        'dim-gray': '#656565',
        'light-gray': '#818181',
        'snow-white': '#FCFCFC',
        'dark-blue': '#031640',
        'gunmetal-gray': '36393E',
        'crimson-red': '#E21D1D',
        'grayish-blue': '#9FA1AD',
        'suva-gray': '#8B8B8B',
        'vulcan': '#36393E',
      }),
      fontFamily: {
        inter: ['Inter var', ...defaultTheme.fontFamily.sans],
      },
      screens: {
        'max-1712': { 'max': '1712px' }, 
        'max-1530': {'max': '1540px'},
        'max-1355': {'max': '1355px'},
        'max-1200': {'max': '1200px'},
        'max-640': {'max': '640px'},
        'max-520': {'max': '520px'},
        'max-470': {'max': '470px'},
        'max-330': {'max': '330px'},
      },
    },
  },
  plugins: [
    function ({ addUtilities }) {
      addUtilities({
        '.no-scrollbar::-webkit-scrollbar': {
          display: 'none',
        },
        '.no-scrollbar': {
          '-ms-overflow-style': 'none',
          'scrollbar-width': 'none',
        },
      });
    },
  ],
};

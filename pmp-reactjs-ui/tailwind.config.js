/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
  content: [
    "./src/**/*",
  ],
  theme: {
    extend: {
      backgroundColor: theme => ({
        ...theme('colors'),
        'anti-flash-white': '#F1F3F6',
        'tory-blue': '#1447b2',
        'fruit-salad': '#589D4C',
        'moderate-red': '#B43F3F',
        'snow-white': '#FCFCFC',
        'medium-gray': '#707070',
        'platinum-gray': '#EBEBEB'
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
        'grayish-blue': '#9FA1AD'
        'suva-gray': '#8B8B8B',
        'vulcan': '#36393E',
      }),
      fontFamily: {
        inter: ['Inter var', ...defaultTheme.fontFamily.sans],
      },
    },
  },
  plugins: [],
};

/** @type {import('tailwindcss').Config} */
export default {
	content: ["./src/main/resources/**/*.{html,js}"],
	theme: {
		extend: {
			colors: {
				'pink': '#FF4191',
				'darkpink': '#E90074',
				'yellow': '#FFF078',
				'black': '#000000',
				'white': '#ffffff',
				'button-pink': '#fd399a',

				'base-pink': {
					100: '#ffcee5',
					200: '#ff9ac6',
					300: '#ff63a6',
					400: '#ff368a',
					500: '#ff1879',
					600: '#ff0171',
					700: '#e5005f',
					800: '#cd0054',
					900: '#b40048',
				},

				'base-yellow': {
					100: '#fffacb',
					200: '#fff49a',
					300: '#ffee64',
					400: '#ffe938',
					500: '#ffe51d',
					600: '#ffe409',
					700: '#e3ca00',
					800: '#cab300',
					900: '#ae9b00',
				}
			}
		},
	},
	plugins: [require('flowbite/plugin')],
	darkMode: "selector",
}
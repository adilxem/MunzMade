console.log("hello")

let currentTheme = getTheme();

document.addEventListener('DOMContentLoaded', () => {

    changeTheme();
});

function changeTheme() {

    changePageTheme(currentTheme, currentTheme);

    const changeThemeButton = document.querySelector('#change-theme-button');
    
    changeThemeButton.addEventListener("click", (event) => {

        let oldTheme = currentTheme;
        
        console.log("change theme button clicked");


        if (currentTheme === "dark") {

            currentTheme = "light";
        }

        else {

            currentTheme = "dark";
        }
       
        changePageTheme(currentTheme, oldTheme);
        
    });

}


// set theme to localStorage --> this function will set the theme (either dark or light) to localStorage, which will be passed by us (on click)

function setTheme(theme) {

    localStorage.setItem("theme", theme);

    console.log(theme);
    

}


function getTheme() {

    let theme = localStorage.getItem("theme");

    return theme ? theme : "dark";
}


function changePageTheme(theme, oldTheme) {

    setTheme(theme);

    if (oldTheme) {

        
        document.querySelector('html').classList.remove(oldTheme);

    }


    document.querySelector('html').classList.add(theme);

}
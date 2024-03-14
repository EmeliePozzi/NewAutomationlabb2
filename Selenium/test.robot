*** Settings ***
Documentation   Denna fil innehåller testfall för att validera funktionaliteten på biluthyrningswebbplatsen. Testfallen täcker inloggning, bokning av bil, felhantering och navigering till olika sidor på webbplatsen.
Library     SeleniumLibrary
Library     DateTime
Test Setup     Startsida

*** Variables ***
${URL}  https://rental18.infotiv.net/
${CorrectEmail}    blubb@hu.com
${IncorrectEmail}    hej@hu.com
${password}     123456



*** Test Cases ***
Testar navigations-flödet av att boka en bil.
    Given att användaren har Loggat in    ${CorrectEmail}     ${password}
    When användaren väljer datum
    And användaren klickar på continue
    And användaren väljer bil att boka
    And lägger in kortuppgifter
    Then ska bil bokas och bekräftelse visas

Testar att avboka en bil
    Given att användaren har Loggat in    ${CorrectEmail}     ${password}
    When användaren går till myPage
    And användaren klickar på cancel booking
    Then ska ett meddelande visas om att bilen är avbokad
    
Testar ABOUT-sidan
     Given att användaren har Loggat in    ${CorrectEmail}     ${password}
     When användaren klickar på ABOUT
     Then skall användaren hamna på ABOUT-sidan

Testar reset-button datum
    Given att användaren har Loggat in    ${CorrectEmail}     ${password}
    And användaren väljer datum
    When användaren reset date
    Then skall datumen vara dagsdatum igen


Fel uppgifter inloggning ska visa felmeddelande
    When användaren fyller i fel uppgifter    ${IncorrectEmail}   ${password}
    Then ska ett felmeddelande visas

Fel kortuppgift ska INTE boka en bil
    Given att användaren har Loggat in   ${CorrectEmail}     ${password}
    When användaren väljer datum
    And användaren klickar på continue
    And användaren väljer bil att boka
    And fel kortuppgifter skrivs in
    Then ska bilen INTE bokas


*** Keywords ***
Startsida
    [Documentation]     Startar upp websidan
    [Tags]      VG_test
    Open Browser    browser=Chrome
    Go To           ${URL}

att användaren har Loggat in
    [Documentation]     Loggar in
    [Tags]      VG_test
    [Arguments]     ${email}    ${password}
    Input Text    //input[@id='email']    ${email}
    Input Password    //input[@id='password']    ${password}
    Click Button    //*[@id="login"]
    Wait Until Element Is Visible    //button[@id='logout']

användaren väljer datum
   [Documentation]     Väljer ett slut-datum 5 dagar fram i tiden
   [Tags]      VG_test
   Wait Until Page Contains Element     //*[@id="questionText"]
   ${currentDate}    Get Current Date    result_format=%Y-%m-%d
   ${futureDate}=       Add Time To Date    ${currentDate}    1 days
    Input Text    //input[@id='end']    0330

användaren klickar på continue
   [Documentation]     Väljer att gå vidare
   [Tags]      VG_test
   Click Button    //*[@id="continue"]

användaren väljer bil att boka
    [Documentation]     Väljer en bil
    [Tags]      VG_test
    Wait Until Element Contains    //h1[@id='questionText']     What would you like to drive?
    Click Button    //*[@id="bookQ7pass5"]  #Bokar en Audi Q7
    
lägger in kortuppgifter
    [Documentation]     Lägger in kortuppgifter
    [Tags]      VG_test
    Wait Until Element Contains    //h1[@id='questionText']        Confirm booking of
    Input Text    //input[@id='cardNum']    1234567891234569
    Input Text    //input[@id='fullName']    John Doe
    Select From List By Index    //*[@id="confirmSelection"]/form/select[1]     2
    Select From List By Index    //select[@title='Year']        7
    Input Text    //input[@id='cvc']    000

ska bil bokas och bekräftelse visas
    [Documentation]     Bokar bilen
    [Tags]      VG_test
    Click Button    //button[@id='confirm']
    Wait Until Element Contains    //label[@class='mediumText']    You can view your booking on your page
    Wait Until Element Contains    //h1[@id='questionTextSmall']    A Audi Q7 is now ready for pickup
    Close Browser
    
användaren går till myPage
    [Documentation]     Test som går till myPage
    [Tags]      Avboka bil
    Click Button    //button[@id='mypage']
    
användaren klickar på cancel booking
    [Documentation]     Klickar på cancel booking. Observera att detta test endast går igenom om föregående test körts minst en gång.
    [Tags]      Avboka bil
    Click Button    //button[@id='unBook1']
    Handle Alert    ACCEPT
    
ska ett meddelande visas om att bilen är avbokad
    [Documentation]     Ett meddelande ska visas om att bilen är returnerad.
    [Tags]      Avboka bil
    Wait Until Page Contains    has been Returned
    Close Browser

användaren fyller i fel uppgifter
    [Documentation]     Negativt test som försöker logga in med ogiltiga uppgifter - Header functionality
    [Tags]      NegativtTest1
    [Arguments]     ${email}    ${password}
    Input Text    //input[@id='email']    ${email}
    Input Text    //input[@id='password']    ${password}
    Click Button    //*[@id="login"]

ska ett felmeddelande visas
    [Documentation]     Ett felmeddelande ska visas "If the user inserts the wrong information a error message appears to the left of the buttons."
    [Tags]      NegativtTest1
    Wait Until Page Contains    Wrong e-mail or password
    Close Browser

fel kortuppgifter skrivs in
    [Documentation]     Negativt test som lägger in fel kortuppgifter - Confirm Booking functionality
    [Tags]      NegativtTest2
    Input Text    //input[@id='cardNum']    1234
    Input Text    //input[@id='fullName']    John Doe
    Select From List By Index    //*[@id="confirmSelection"]/form/select[1]     2
    Select From List By Index    //select[@title='Year']        7
    Input Text    //input[@id='cvc']    000
    Click Button    //button[@id='confirm']

ska bilen INTE bokas
    [Documentation]     Negativt test som kontrollerar att bil inte bokas om fel uppgifter skrivs in
    [Tags]      NegativtTest2
    Page Should Not Contain    //*[@id="questionTextSmall"]
    Page Should Not Contain    //label[@class='mediumText']    You can view your booking on your page
    Close Browser
    
användaren klickar på ABOUT
    [Documentation]     Test som kontrollerar att about-sidan visar rätt information " The About text will take you to the about page" - Header functionality
    [Tags]      ValfriFunktion1
    Click Link    //a[@id='about']
        
skall användaren hamna på ABOUT-sidan
    [Documentation]     Test som kontrollerar att about-sidan visar rätt information " The About text will take you to the about page" - Header functionality
    [Tags]      ValfriFunktion1
    Wait Until Element Contains    //h1[@id='questionText']    Welcome
    Wait Until Element Contains    //label[contains(text(),'This project was created at an internship at Infot')]    This project was created at an internship at Infotiv AB, Gothenburg, by Joakim Gustavsson and Johan Larsson, with the help of Maheel Dabarera. The project consists of a mock car rental homepage, to be used as a system under test for educational purposes.
    Wait Until Element Is Visible    //div[@id='mainWrapperBody']//a[1]
    Wait Until Element Is Visible    //*[@id="linkButton"]
    Close Browser

användaren reset date
    [Documentation]     Test som kontrollerar att reset-knapp återställer datum till dagsdatum "The reset button will refresh the page and set dates to today." - Date selection functionality
    [Tags]      ValfriFunktion2
    Click Button    //*[@id="reset"]
    
skall datumen vara dagsdatum igen
    [Documentation]     Test som kontrollerar att reset-knapp återställer datum till dagsdatum "The reset button will refresh the page and set dates to today." - Date selection functionality
    [Tags]      ValfriFunktion2
    ${currentDate}    Get Current Date    result_format=%Y-%m-%d

    #Hämtar värdet på attributet value(datumet) från HTML-elementet med id start.
    ${actualStartDate}    Get Element Attribute    //input[@id='start']    value
    ${actualEndDate}    Get Element Attribute    //input[@id='end']    value

    #Konverterar till String och jämför de två variablerna med varandra.
    Should Be Equal As Strings    ${actualStartDate}    ${currentDate}
    Should Be Equal As Strings    ${actualEndDate}    ${currentDate}
    Close Browser


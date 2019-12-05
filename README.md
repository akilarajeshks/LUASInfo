# LUASInfo

Simple application that displays LUAS information from a network fetch. 

It primarily uses: 
  1. MVVM arcitecture
  2. Retrofit 
  3. Koin 
  4. Navigation component
  5. Tikxml - XML converter
  
 *Reason for not storing data* : Information for any transportation needs to be current data and should not show any previously loaded data
 
 **Improvements** 
 
This app is structured based on features, hence it has a Listing **Package** (which shows the list of trams) that can be made as a **module** when more features are to be added. 

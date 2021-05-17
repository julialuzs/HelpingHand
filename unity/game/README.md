<div align="center">
  <a href="https://www.vlibras.gov.br/">
    <img
      alt="VLibras"
      src="https://vlibras.gov.br/assets/imgs/IcaroGrande.png"
    />
  </a>
</div>

# VLibras Mobile (Android and IOS)

VLibras Mobile is an mobile device application which is installed in your device running either Android or iOS operational system.

![Version](https://img.shields.io/badge/version-v3.4.0-blue.svg)
![Platform](https://img.shields.io/badge/platform-android%20%7C%20ios-lightgrey.svg)
![License](https://img.shields.io/badge/license-LGPLv3-blue.svg)
![VLibras](https://img.shields.io/badge/vlibras%20suite-2019-green.svg?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAUCAYAAAC9BQwsAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAA3XAAAN1wFCKJt4AAAAB3RJTUUH4wIHCiw3NwjjIgAAAQ9JREFUOMuNkjErhWEYhq/nOBmkDNLJaFGyyyYsZzIZKJwfcH6AhcFqtCvFDzD5CQaTFINSlJJBZHI6J5flU5/P937fube357m63+d+nqBEagNYA9pAExgABxHxktU3882hjqtd9d7/+lCPsvpDZNA+MAXsABNU6xHYQ912ON2qC2qQ/X+J4XQXEVe/jwawCzwNAZp/NCLiDVgHejXgKIkVdGpm/FKXU/BJDfytbpWBLfWzAjxVx1Kuxwno5k84Jex0IpyzdN46qfYSjq18bzMHzQHXudifgQtgBuhHxGvKbaPg0Klaan7GdqE2W39LOq8OCo6X6kgdeJ4IZKUKWq1Y+GHVjF3gveTIe8BiCvwBEZmRAXuH6mYAAAAASUVORK5CYII=)

## Table of Contents

- [Getting Started](#getting-started)
  - [System Requirements](#system-requirements)
  - [Prerequisites](#prerequisites)
  - [Building](#building)
- [Contributors](#contributors)
- [License](#license)


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### System Requirements

* OS: Windows 7 SP1+, 8, 10, 64-bit versions only | Mac OS X 10.11+.
* GPU : Graphics card with DX10 (shader model 4.0).
* Memory: 8 GB

### Prerequisites

In order to build and use a version of VLibras Mobile, some preinstallations are required.

[Android Studio](https://developer.android.com/studio)

In order to install Android Studio, make sure you download the Windows or Mac Version of the application and install the latest version of it.

```
Visit and download it on: https://developer.android.com/studio
```

[Blender](https://www.blender.org/)

In order to install Blender, make sure yoy download the Windows or Mac Version of the application and install the latest version of it.

```
Visit and download it on: https://www.blender.org/download/
```
[Unity](https://unity.com/pt)

The last but not less important installation requisite is the Unity Player. In order to install it, make sure you get the Windows or Mac Version of the application. The version used must be *Unity 2018.3.14*.

```
Visit and download it on: https://unity3d.com/pt/get-unity/download/archive
```
### Building

After installing all the prerequisites, run the project by opening it in Unity Player Hub. Search for the root folder of the project and select it in the Explorer window opened by Unity.

To build the project you must follow the steps:

1. Go to the Build Settings (*File -> Build Settings*);
2. Select **Android** or **IOS** as target platform. (You may need to download target platform dependencies in this step);
3. Navigate through the project folders in the Unity Project Structure and open the Scene **Main**. (*Assets -> Scenes -> Main*)
3. (*Android only*) Go to Player Settings (*File -> Build Settings -> Player Settings*) and make sure the minimum API level is set to **API Level 21** and target API Level is set to **Automatic (Highest Installed)**.
4. Start the player build. (If you are running the project on Windows, the IOS Build will export a Xcode Project to be built on a Mac OS);

## Contributors

* Mateus Pires - <mateuspires@lavid.ufpb.br>
* Vinícius Guedes - <viniciuesguedes@lavid.ufpb.br>
* Thiago Filipe - <thiago.filipe@lavid.ufpb.br>

## License

This project is licensed under the LGPLv3 License - see the [LICENSE](LICENSE) file for details.

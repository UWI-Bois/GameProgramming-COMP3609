using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MenuManager : MonoBehaviour
{
    [System.Obsolete]
    public void StartGame()
    {
        Application.LoadLevel("Level1");
    }

    public void QuitGame()
    {
        Debug.Log("Exited Game!!");
        Application.Quit();
    }
}

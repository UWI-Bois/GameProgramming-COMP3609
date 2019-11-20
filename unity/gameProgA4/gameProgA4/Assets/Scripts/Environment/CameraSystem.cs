using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraSystem : MonoBehaviour
{

    private GameObject Player = null; // grab the player
    public float xMin, xMax, yMin, yMax; // these clamp the camera into a certain pos


    // Start is called before the first frame update
    void Start()
    {
        Player = GameObject.FindGameObjectWithTag("Player"); // find the player, check tags and layers to add new tags
        if (Player == null) Debug.LogError("cannot find player gameobject from camersystem!");
    }

    private void LateUpdate()
    {
        float x = Mathf.Clamp(Player.transform.position.x, xMin, xMax);
        float y = Mathf.Clamp(Player.transform.position.y, yMin, yMax);
        transform.position = new Vector3(
            x, y, transform.position.z
        );
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}

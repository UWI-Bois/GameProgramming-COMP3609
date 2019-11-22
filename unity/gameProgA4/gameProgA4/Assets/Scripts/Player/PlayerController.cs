using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI; // access ui elements

public class PlayerController : MonoBehaviour
{
    public int jumpForce;
    public int speed, maxSpeed;

    public int health, maxHealth;
    public int score, exp, level, toNextLevel;
    public int damage;
    public bool win;

    public bool facingLeft, idle;
    public bool hasDied;
    public bool isGrounded;
    public int direction;

    private float moveX, moveY;
    private float distGround;
    private Rigidbody2D rb;
    private Collider2D col;
    private Vector2 size;
    private float timeLeft, timeElapsed;
    public Animator animator;

    public GameObject timeLeftUI;
    public GameObject playerScoreUI;
    private int coinVal;

    // Start is called before the first frame update
    void Start()
    {
        maxSpeed = 10;
        idle = true;
        direction = 1; // 1 = right, -1 = left
        coinVal = 10;
        timeLeft = 120f;
        timeElapsed = 0f;
        win = false;
        exp = 0;
        toNextLevel = 10;
        level = 1;
        damage = 1;
        rb = GetComponent<Rigidbody2D>();
        col = GetComponent<Collider2D>();
        animator = GetComponent<Animator>();
        facingLeft = false;
        isGrounded = false;
        jumpForce = 300;
        hasDied = false;
        speed = 0;
        maxHealth = 4;
        health = maxHealth;
        score = 0;
        size = col.bounds.size;
        distGround = col.bounds.extents.y;
        rb.freezeRotation = true;
        //print(size.ToString() + col.ToString());
    }

    // Update is called once per frame
    void FixedUpdate()
    {
        timeLeft -= Time.deltaTime;
        timeLeft += Time.deltaTime;
        Move(); // move the player
        CheckY(); // check to see if you fell off the map
        PlayerRaycast(); // used to kill enemies
        //TODO make a tilemap for walls ( essentially keep a tilemap for groundable alone, to facillitate hanging)
        updateGUI();
    }

    private void CheckY()
    {
        // this function will check the y value of the player, as well as the y velocity
        // this is actual dogshit garbage and you should feel bad for using this
        //if ((Vector3)rb.velocity == Vector3.zero) isGrounded = true; 
        //else isGrounded = false;
        int yDead = -10;
        if (transform.position.y <= yDead && !hasDied)
        {
            Debug.Log("you fell to death!");
            RestartGame();
        }
    }

    void LoadMenu()
    {
        SceneManager.LoadScene("MainMenu");
    }

    void ResetPlayer()
    {
        health = maxHealth;
        hasDied = false;
        score /= 2;
    }

    void RestartGame()
    {
        Die();
        ResetPlayer();
        LoadMenu();
    }

    private IEnumerator Die()
    {
        hasDied = true;
        yield return new WaitForSeconds(1.5f);
    }

    void updateGUI()
    {
        timeLeftUI.gameObject.GetComponent<Text>().text = "Time: " + (int)timeLeft;
        playerScoreUI.gameObject.GetComponent<Text>().text = "Score: " + score;
    }

    void Move()
    {
        speed = maxSpeed;
        idle = false;
        // controls
        moveX = Input.GetAxis("Horizontal");
        moveY = Input.GetAxis("Vertical");
        animator.SetFloat("XSpeed", Mathf.Abs(rb.velocity.x));
        animator.SetFloat("YSpeed", rb.velocity.y);
        animator.SetInteger("direction", direction);
        //if (Input.GetButtonDown("Horizontal")) ; // cool code to check button
        
        if (Input.GetButtonDown("Jump") && isGrounded) Jump();
        // animations
        // player direction
        if (moveX < 0.0f && !facingLeft) FlipPlayer();
        else if (moveX > 0.0f && facingLeft) FlipPlayer();
        // physics
        rb.velocity = new Vector2(
            moveX * speed,
            rb.velocity.y
        );
    }

    void FlipPlayer()
    {
        facingLeft = !facingLeft;
        direction *= -1;
        Vector2 localScale = transform.localScale;
        localScale.x *= -1;
        transform.localScale = localScale;
    }

    void OnLanding()
    {
        isGrounded = true;
        animator.SetBool("isGrounded", true);
    }

    void Jump()
    {
        //animator.SetFloat("YSpeed", jumpForce);
        rb.AddForce(Vector2.up * jumpForce);
        isGrounded = false;
        animator.SetBool("isGrounded", isGrounded);
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        // so using tilemaps, we can make new tilemaps and assign different tags to them, for ex: water and ground.
        //Debug.Log("player has collided with " + collision.collider.name + " with tag: " + collision.gameObject.tag);
        if (collision.gameObject.tag == "groundable") OnLanding();
        
    }
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.name == "Goal") NextLevel();
        if(collision.name == "Coin") EatCoin(collision);
    }

    private void EatCoin(Collider2D collision)
    {
        score += coinVal;
        Destroy(collision.gameObject);
    }

    private void NextLevel()
    {
        CountScore();
        // add exp, manage time
    }

    void CountScore()
    {
        score += (int)timeLeft * 10;
        Debug.Log(score);
    }

    IEnumerator waitSeconds(float seconds)
    {
        yield return new WaitForSeconds(seconds);
    }

    void PlayerRaycast()
    {
        // use this to kill enemies
        // every time this ray touches an enemy, bounce off his head
        RaycastHit2D rayDown = Physics2D.Raycast(
            transform.position,
            Vector2.down // shoot a ray down
        );
        // cast a ray from the top of the player
        RaycastHit2D rayUp = Physics2D.Raycast(
            transform.position,
            Vector2.up // shoot a ray down
        );

        if (rayDown == false || rayDown.collider == null) return;

        if (rayDown.distance < 0.9f && rayDown.collider.tag == "Enemy") // kill enemy
        {
            rb.AddForce(Vector2.up * jumpForce); // force jump
            rayDown.collider.gameObject.GetComponent<Rigidbody2D>().AddForce(Vector2.right * 200);
            rayDown.collider.gameObject.GetComponent<Rigidbody2D>().gravityScale = 10;
            rayDown.collider.gameObject.GetComponent<Rigidbody2D>().freezeRotation = false; 
            rayDown.collider.gameObject.GetComponent<BoxCollider2D>().enabled = false; // grab the component of the enemy
            rayDown.collider.gameObject.GetComponent<EnemyController>().enabled = true; // disable the controller script assigned to the enemy
            // destroy the enemy object after a certan time??
            //StartCoroutine(waitSeconds(3)); // wait 3 sec
            Destroy(rayDown.collider.gameObject);
        }

        //if(rayUp.distance < 0.9f && rayUp.collider.tag == "name")

    }

}
